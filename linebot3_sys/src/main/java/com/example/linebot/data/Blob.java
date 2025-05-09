package com.example.linebot.data;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import com.linecorp.bot.client.LineBlobClient;
import com.linecorp.bot.client.MessageContentResponse;
import java.io.InputStream;

@Repository
public class Blob {

    // LINEの画像保存場所にアクセスするためのクラス
    // (専用のブラウザのようなもの)
    private LineBlobClient blob;

    public Blob(LineBlobClient blob) {
        this.blob = blob;
    }

    public Resource getImageResource(MessageEvent<ImageMessageContent> event) throws Exception{

        // 送られてきたLINEのメッセージID(一つ一つのメッセージにIDがついている)を取得する
        String msgId = event.getMessage().getId();

        // Blob (LINEの画像保存場所)からメッセージIDと対応する画像の取得準備をする
        MessageContentResponse contentResponse = blob.getMessageContent(msgId).get();

        try (InputStream is = contentResponse.getStream()){
            // 画像データをバイトデータとして取得する
            // 画像が期限切れの場合などには、例外が発生する
            LINEImageResourse resourse = new LINEImageResourse(is.readAllBytes());
            return resourse;
        }
    }
}
