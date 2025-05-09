package com.example.linebot.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KaoninsikiResponse(
        int low, int high, String gender, float gender_con, float calm, float confused,
        float surprised, float fear, float sad, float angry, float disgusted, float happy) {
}
