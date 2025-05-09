package com.example.linebot.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JankenResponse(int jibun, int aite, int kekka) {
}
