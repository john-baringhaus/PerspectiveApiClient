package com.github.cuponadesk.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyzeCommentResponse {

  @JsonProperty("attributeScores")
  private Map<String, AttributeScores> attributeScores = new HashMap<>() {
    {
      put("TOXICITY", new AttributeScores());
      put("SEVERE_TOXICITY", new AttributeScores());
      put("IDENTITY_ATTACK", new AttributeScores());
      put("INSULT", new AttributeScores());
      put("THREAT", new AttributeScores());
    }
  };


  @JsonProperty("languages")
  List<String> languages = new ArrayList<>();

  @JsonProperty("clientToken")
  private String clientToken;

  @Override
  public String toString() {
    return "AnalyzeCommentResponse{" +
           "attributeScores=" + attributeScores +
           ", languages=" + languages +
           ", clientToken='" + clientToken + '\'' +
           '}';
  }
}
