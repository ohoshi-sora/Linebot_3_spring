package com.example.linebot.service;

import com.example.linebot.data.Blob;
import com.example.linebot.data.FamousAPI;
import com.example.linebot.data.FamousLog;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class FamousService {
    // データ層：LINEのデータ格納領域にアクセスするクラス
    private Blob blob;

    // データ層：皆さんのAWS EC2のfamousプログラムにアクセスするクラス
    private FamousAPI famousAPI;

    // データ層：データベースのfamous_logにアクセスするクラス
    private FamousLog famousLog;

    public FamousService(Blob blob, FamousAPI famousAPI, FamousLog famousLog) {
        this.blob = blob;
        this.famousAPI = famousAPI;
        this.famousLog = famousLog;
    }

    public FamousResponse doFamous (MessageEvent<ImageMessageContent> event) throws Exception{

        // 画像データを取得する
        Resource imageResource = blob.getImageResource(event);

        // 有名人認識を実行する
        FamousResponse famousResponse = famousAPI.go(imageResource);

        // データベースを用いて永続化
        famousLog.insert(famousResponse);
        // ビジネスサービスの処理結果を返す
        return famousResponse;

    }
}
