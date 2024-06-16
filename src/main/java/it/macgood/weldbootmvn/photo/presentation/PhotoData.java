package it.macgood.weldbootmvn.photo.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public record PhotoData(
        @JsonProperty("1")
        Integer data1,
        @JsonProperty("2")
        Integer data2,
        @JsonProperty("3")
        Integer data3,
        @JsonProperty("4")
        Integer data4,
        @JsonProperty("5")
        Integer data5,
        @JsonProperty("6")
        Integer data6
) {
}
