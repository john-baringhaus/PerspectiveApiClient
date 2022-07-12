package com.github.cuponadesk.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnalyzeCommentRequest {

  public static final String TOXICITY_ATTRIBUTE        = "TOXICITY";
  public static final String SEVERE_TOXICITY_ATTRIBUTE = "SEVERE_TOXICITY";
  public static final String IDENTITY_ATTACK_ATTRIBUTE = "IDENTITY_ATTACK";
  public static final String INSULT_ATTRIBUTE          = "INSULT";
  public static final String PROFANITY_ATTRIBUTE       = "PROFANITY";
  public static final String THREAT_ATTRIBUTE          = "THREAT";

  @JsonProperty(value = "comment", required = true)
  private Comment comment;

  @JsonProperty("context")
  private List<Entry> entries;

  @Builder.Default
  @JsonProperty(value = "requestedAttributes", required = true)
  private Map<String, RequestedAttributes> requestedAttributes = new HashMap<>();

  @JsonProperty("languages")
  private List<String> languages;

  @Builder.Default
  @JsonProperty("doNotStore")
  private boolean doNotStore = true;

  @JsonProperty("clientToken")
  private String clientToken;

  @JsonProperty("sessionId")
  private String sessionId;

  public void addAttribute(String attribute) {
    this.addAttribute(attribute, 0.0F);
  }

  public void addAttribute(String attribute, float floor) {
    requestedAttributes.put(attribute, new RequestedAttributes("PROBABILITY", floor));
  }

  @Getter
  @Setter
  @Builder
  public static class Comment {

    @JsonProperty(value = "text", required = true)
    private String text;
    @JsonProperty("type")
    private String type;
  }
}
