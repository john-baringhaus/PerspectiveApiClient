package com.github.cuponadesk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cuponadesk.DTO.AnalyzeCommentRequest;
import com.github.cuponadesk.DTO.AnalyzeCommentResponse;
import com.github.cuponadesk.DTO.Attributes;
import com.github.cuponadesk.DTO.PerspectiveCredentials;
import com.github.cuponadesk.DTO.RequestedAttributes;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class PerspectiveClient {

  private static final String[] DEFAULT_VALID_CREDENTIALS_FILE_NAMES = {"test-perspective-credentials.json", "perspective-credentials.json"};
  private static final String   API_URL                              = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=";

  public static final ObjectMapper
      OBJECT_MAPPER =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .findAndRegisterModules();

  private final PerspectiveCredentials perspectiveCredentials;

  public PerspectiveClient() {
    this(getAuthentication());
  }

  public PerspectiveClient(PerspectiveCredentials perspectiveCredentials) {
    this.perspectiveCredentials = perspectiveCredentials;
  }

  public PerspectiveClient(String apiKey) {
    this.perspectiveCredentials = PerspectiveCredentials.builder().apiKey(apiKey).build();
  }

  public static PerspectiveCredentials getAuthentication() {
    String credentialPath = System.getProperty("perspective.credentials.file.path");
    if (credentialPath != null) {
      return getAuthentication(new File(credentialPath));
    } else {
      return getAuthentication(Paths.get(""));
    }
  }

  public static PerspectiveCredentials getAuthentication(final Path pathToScan, final String... validNames) {
    if (pathToScan.toFile().isFile()) {
      return getAuthentication(pathToScan.toFile());
    } else {
      String[] namesToCheck = validNames != null && validNames.length > 0 ? validNames : DEFAULT_VALID_CREDENTIALS_FILE_NAMES;
      for (Path currentPath = pathToScan; currentPath != null; currentPath = currentPath.getParent()) {
        for (String name : namesToCheck) {
          Path file = currentPath.resolve(name);
          if (Files.isRegularFile(file)) {
            return getAuthentication(file.toFile());
          }
        }
      }
    }
    return null;
  }

  public static PerspectiveCredentials getAuthentication(File twitterCredentialsFile) {
    try {
      PerspectiveCredentials perspectiveCredentials = PerspectiveClient.OBJECT_MAPPER.readValue(twitterCredentialsFile, PerspectiveCredentials.class);
      if (perspectiveCredentials.getApiKey() == null) {
        throw new RuntimeException("Unable to load credentials");
      }
      return perspectiveCredentials;
    } catch (Exception e) {
      return null;
    }
  }

  public AnalyzeCommentResponse execute(AnalyzeCommentRequest commentRequest) {

    if (commentRequest.getRequestedAttributes().entrySet().size() == 0) {
      commentRequest.getRequestedAttributes().put(Attributes.TOXICITY_ATTRIBUTE, new RequestedAttributes());
    }

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

      HttpPost request = new HttpPost(API_URL + perspectiveCredentials.getApiKey());
      request.setHeader("Content-type", "application/json");

      StringEntity requestEntity = new StringEntity(OBJECT_MAPPER.writeValueAsString(commentRequest), ContentType.APPLICATION_JSON);
      request.setEntity(requestEntity);

      try (CloseableHttpResponse response = httpClient.execute(request)) {

        HttpEntity entity = response.getEntity();
        if (entity != null) {
          // return it as a String
          String                 r      = EntityUtils.toString(entity, "UTF-8");
          AnalyzeCommentResponse result = OBJECT_MAPPER.readValue(r, AnalyzeCommentResponse.class);
          System.out.println(result);
          return result;
        }
      }
    } catch (Exception ignored) {
    }
    return null;
  }
}
