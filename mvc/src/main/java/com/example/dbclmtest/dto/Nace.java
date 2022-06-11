package com.example.dbclmtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Nace(
    @JsonProperty("Order")
    Integer order,
    @JsonProperty("Level")
    int level,
    @JsonProperty("Code")
    String code,
    @JsonProperty("Parent")
    String parent,
    @JsonProperty("Description")
    String description,
    @JsonProperty("This item includes")
    String thisItemIncludes,
    @JsonProperty("This item also includes")
    String thisItemAlsoIncludes,
    @JsonProperty("Rulings")
    String rulings,
    @JsonProperty("This item excludes")
    String thisItemExcludes,
    @JsonProperty("Reference to ISIC Rev. 4")
    String referenceToISIC
) {
}
