package com.github.cuponadesk.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeScores {

  @JsonProperty("summaryScore")
  private Score summaryScore;

  @JsonProperty("spanScores")
  private List<SpanScore> spanScores = new ArrayList<>();

  @Override
  public String toString() {
    return "AttributeScores{" +
           "summaryScore=" + summaryScore +
           ", spanScores=" + spanScores.toString() +
           '}';
  }

  @Getter
  @Setter
  private static class Score {

    @JsonProperty("value")
    private float  value;
    @JsonProperty("type")
    private String type;

    @Override
    public String toString() {
      return "Score{" +
             "value=" + value +
             ", type='" + type + '\'' +
             '}';
    }
  }

  @Getter
  @Setter
  private static class SpanScore {

    @JsonProperty("begin")
    private int   begin;
    @JsonProperty("end")
    private int   end;
    @JsonProperty("score")
    private Score score;

    @Override
    public String toString() {
      return "SpanScore{" +
             "begin=" + begin +
             ", end=" + end +
             ", score=" + score +
             '}';
    }
  }
}
