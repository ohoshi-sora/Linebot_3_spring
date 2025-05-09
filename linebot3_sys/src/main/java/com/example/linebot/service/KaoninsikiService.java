package com.example.linebot.service;

import com.example.linebot.data.Blob;

import com.example.linebot.data.KaoninsikiAPI;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class KaoninsikiService {

    // データ層：LINEのデータ格納領域にアクセスするクラス
    private Blob blob;

    // データ層：AWS EC2のジャンケンプログラムにアクセスするクラス
    private KaoninsikiAPI kaoninsikiAPI;

    public KaoninsikiService(Blob blob, KaoninsikiAPI kaoninsikiAPI) {
        this.blob = blob;
        this.kaoninsikiAPI = kaoninsikiAPI;
    }

    public KaoninsikiResponse doKaoninsiki (MessageEvent<ImageMessageContent> event) throws Exception{

        // 画像データを取得する
        Resource imageResource = blob.getImageResource(event);

        // 顔認識を実行する
        KaoninsikiResponse kaoninsikiResponse = kaoninsikiAPI.play(imageResource);

        // ビジネスサービス(顔認識)の処理結果を返す
        return kaoninsikiResponse;

    }


}
