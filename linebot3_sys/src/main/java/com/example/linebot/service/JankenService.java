package com.example.linebot.service;

import com.example.linebot.data.Blob;
import com.example.linebot.data.JankenAPI;
import com.example.linebot.data.JankenLog;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class JankenService {

    // データ層：LINEのデータ格納領域にアクセスするクラス
    private Blob blob;

    // データ層：皆さんのAWS EC2のジャンケンプログラムにアクセスするクラス
    private JankenAPI jankenAPI;

    // データ層：データベースのjanken_logにアクセスするクラス
    private JankenLog jankenLog;

    public JankenService(Blob blob, JankenAPI jankenAPI, JankenLog jankenLog) {
        this.blob = blob;
        this.jankenAPI = jankenAPI;
        this.jankenLog = jankenLog;
    }


    public JankenResult doJanken (MessageEvent<ImageMessageContent> event) throws Exception{

        // 画像データを取得する
        Resource imageResource = blob.getImageResource(event);

        // ジャンケンを実行する
        JankenResponse jankenResponse = jankenAPI.playGame(imageResource);

        // ジャンケンの結果を永続化する
        jankenLog.insert(jankenResponse);

        // ビジネスサービス(ジャンケン)の処理結果を返す
        JankenResult jankenResult =
                new JankenResult(imageResource.contentLength(), jankenResponse);
        return jankenResult;

    }

}
