package com.example.linebot.service;

import com.example.linebot.data.Blob;
import com.example.linebot.data.TextninsikiAPI;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class TextService {

    // データ層：LINEのデータ格納領域にアクセスするクラス
    private Blob blob;

    // データ層：AWS EC2のジャンケンプログラムにアクセスするクラス
    private TextninsikiAPI textninsikiAPI;

    public TextService(Blob blob, TextninsikiAPI textninsikiAPI) {
        this.blob = blob;
        this.textninsikiAPI = textninsikiAPI;
    }

    public TextResponse doTextninsiki (MessageEvent<ImageMessageContent> event) throws Exception{
        // 画像データを取得する
        Resource imageResource = blob.getImageResource(event);

        // テキスト認識を実行する
        TextResponse textResponse = textninsikiAPI.go(imageResource);

        //  ビジネスサービス(テキスト認識)の処理結果を返す
        return textResponse;
    }
}
