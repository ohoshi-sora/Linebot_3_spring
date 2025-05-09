package com.example.linebot.service;

// gameCountはこれまでのゲーム回数
// jibunWinCountは自分が勝った回数
// jibunWinRateは自分の勝率
// をそれぞれ格納する
public record Senreki(int gameCount, int jibunWinCount, float jibunWinRate) {
}
