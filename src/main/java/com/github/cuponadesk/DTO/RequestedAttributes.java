package com.github.cuponadesk.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestedAttributes {

  @Builder.Default
  @JsonProperty("scoreType")
  private String scoreType = "PROBABILITY";
  @JsonProperty("scoreThreshold")
  private float  scoreThreshold;

}
