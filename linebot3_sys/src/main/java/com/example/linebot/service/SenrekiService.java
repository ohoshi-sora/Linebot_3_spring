package com.example.linebot.service;

import com.example.linebot.data.JankenLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SenrekiService {

    private JankenLog jankenLog;

    public SenrekiService(JankenLog jankenLog) {
        this.jankenLog = jankenLog;
    }

    public Senreki calcSenreki (){

        // データベースから永続化されたJankenResponseのリストを取得する
        List<JankenResponse> jankenResponses = jankenLog.selectALL();

        // (1) ゲーム回数をJankenResponseから計算する
        int gameCount = jankenResponses.size();

        // (2) 自分が勝った回数をJankenResponseから計算する
        int jibunWinCount = 0;

        // kakkaが2の時が自分が勝った時
        for (JankenResponse response : jankenResponses) {
            if (response.kekka() == 2) {
                jibunWinCount++ ;
            }
        }

        // (3) ゲーム回数・自分が勝った回数から自分の勝率(flaot)を計算する
        // ただし、ゲーム回数が0の時は勝率も0にする
        float jibunWinRate = 0;
        if (gameCount == 0){
            jibunWinRate = 0;
        } else {
            jibunWinRate = (float) jibunWinCount / (float)gameCount;
        }

        // (4) Senrekiインスタンスを生成して戻り値にする
        return new Senreki(gameCount, jibunWinCount,jibunWinRate);

    }
}
