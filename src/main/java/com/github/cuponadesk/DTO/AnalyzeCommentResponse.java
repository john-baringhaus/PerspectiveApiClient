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
  private Map<String, AttributeScore> attributeScores = new HashMap<>() {
    {
      put("TOXICITY", new AttributeScore());
      put("SEVERE_TOXICITY", new AttributeScore());
      put("IDENTITY_ATTACK", new AttributeScore());
      put("INSULT", new AttributeScore());
      put("THREAT", new AttributeScore());
    }
  };
  @JsonProperty("languages")
  List<String> languages = new ArrayList<>();

  @JsonProperty("clientToken")
  private String clientToken;

  public float getToxicityScore() {
    return attributeScores.get("TOXICITY").getSummaryScore().getValue();
  }
  public float getSevereToxicityScore() {
    return attributeScores.get("SEVERE_TOXICITY").getSummaryScore().getValue();
  }

  public float getIdentityAttackScore() {
    return attributeScores.get("IDENTITY_ATTACK").getSummaryScore().getValue();
  }
  public float getInsultScore() {
    return attributeScores.get("INSULT").getSummaryScore().getValue();
  }
  public float getThreatScore() {
    return attributeScores.get("THREAT").getSummaryScore().getValue();
  }

  @Override
  public String toString() {
    return "AnalyzeCommentResponse{" +
           "attributeScores=" + attributeScores +
           ", languages=" + languages +
           ", clientToken='" + clientToken + '\'' +
           '}';
  }
}
