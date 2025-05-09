package com.example.linebot.presentation;

import com.example.linebot.presentation.replier.*;
import com.example.linebot.presentation.replier.KaoninsikiReply;
import com.example.linebot.service.*;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.MessageEvent;

import java.util.List;

@LineMessageHandler
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    private JankenService jankenService;
    private SenrekiService senrekiService;
    private KaoninsikiService kaoninsikiService;
    private TextService textService;
    private FamousService famousService;
    private FamousCountService famousPerService;
    private int mode;
    private String modeKao;

    public Callback(JankenService jankenService, SenrekiService senrekiService,
                    KaoninsikiService kaoninsikiService, TextService textService,
                    FamousService famousService, FamousCountService famousPerService) {
        this.jankenService = jankenService;
        this.senrekiService = senrekiService;
        this.kaoninsikiService = kaoninsikiService;
        this.textService = textService;
        this.famousService = famousService;
        this.famousPerService = famousPerService;
        this.mode = 0;
        this.modeKao = "年齢と表情";
    }

    // フォローイベントに対応する
    @EventMapping
    public Message handleFollow(FollowEvent event) {
        // 実際はこのタイミングでフォロワーのユーザIDをデータベースにに格納しておくなど
        Follow follow = new Follow(event);
        return follow.reply();
    }

    // 文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    public Message handleMessage(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();

        if (text.equals("help")){
            return new TextMessage("じゃんけんモードは『じゃんけん』\n" +
                    "ジャンケンの戦歴は『戦歴』\n" +
                    "顔認識モードは『顔認識』\n" +
                    "テキスト認識モードは『テキスト』\n" +
                    "有名人認識モードは『有名人』\n" +
                    "有名人認識のランキングは『ランキング』" +
                    "と入力してね🙌");
        } else if (text.contains("じゃんけん")){
            mode = 1;
            return new TextMessage("じゃんけんの手の画像を送信してね🤌");
        } else if (text.contains("顔認識")){
            mode = 2;
            return new TextMessage("年齢・性別診断は『年齢』\n" +
                    "表情診断は『表情』\n" +
                    "どちらも表示したい場合はそのまま画像を送信してね🤌");
        } else if (mode == 2 && text.contains("年齢")) {
            modeKao = "年齢";
            return new TextMessage("認識したい顔の画像を送信してね");
        } else if (mode == 2 && text.contains("表情")) {
            modeKao = "表情";
            return new TextMessage("認識したい顔の画像を送信してね");
        } else if (text.contains("テキスト")) {
            mode = 3;
            return new TextMessage("テキスト認識したい画像を送信してね");
        } else if (text.contains("有名人")) {
            mode = 4;
            return new TextMessage("有名人認識したい画像を送信してね");
        }
        // 戦歴と言う単語が入っていたら戦歴を表示する
        else if (text.contains("戦歴")){
            Senreki senreki = senrekiService.calcSenreki();
            return new SenrekiReply(senreki).reply();
        } else if (text.contains("ランキング")){
            List<FamousResponse> ranking = famousPerService.calcFamousCount();
            return new FamousCountReply(ranking).reply();
        } else {
            Parrot parrot = new Parrot(event);
            return parrot.reply();
        }
    }


    // プレゼンテーション層から画像付きメッセージを受け取り
    // ビジネスサービス層のJankenServiceクラスのdoJankenメソッドを呼び出し結果を返す
//    @EventMapping
//    public List<Message> handlegazou(MessageEvent<ImageMessageContent> event) throws Exception{
//
//        switch (mode){
//            case 1:
//                // ジャンケンを実施するビジネスサービス層のクラスを呼び出す
//                JankenResult jankenResult = jankenService.doJanken(event);
//                return List.of(new ImageSizeReply(jankenResult).reply(),
//                        new JankenReply(jankenResult).reply());
//            case 2:
//                KaoninsikiResponse kaoninsikiResponse = kaoninsikiService.doKaoninsiki(event);
//                return List.of(new KaoninsikiReply(kaoninsikiResponse).reply(), new TextMessage(""));
//        }
//    }

    // 画像メッセージに対応する
    @EventMapping
    public List<Message> handlegazou(MessageEvent<ImageMessageContent> event) throws Exception{

        List<Message> listMessage = null;

            if (mode == 0) {
                // モード洗濯をしていない状態で写真が送られた場合
                listMessage = List.of(new TextMessage("モードを入力してね😭😭\n\n" +
                        "じゃんけんモードは『じゃんけん』\n" +
                        "顔認識モードは『顔認識』\n" +
                        "テキスト認識モードは『テキスト』\n" +
                        "有名人認識モードは『有名人』\n" +
                        "と入力してね🙌"));
            } else if (mode == 1) {
                // 初期化
                mode = 0;
                try {
                    // ジャンケンを実施するビジネスサービス層のクラスを呼び出す
                    JankenResult jankenResult = jankenService.doJanken(event);
                    listMessage = List.of(new ImageSizeReply(jankenResult).reply(),
                            new JankenReply(jankenResult).reply());
                } catch (Exception e) {
                    return List.of(new TextMessage("画像認識できませんでした😭"));
                }
            } else if (mode == 2) {
                // 初期化
                mode = 0;
                try {
                    // 顔認識モードの時に対応する
                    KaoninsikiResponse kaoninsikiResponse = kaoninsikiService.doKaoninsiki(event);
                    listMessage = List.of(new KaoninsikiReply(kaoninsikiResponse, modeKao).reply());
                    // 初期化
                    modeKao = "年齢と表情";
                } catch (Exception e) {
                    // 初期化
                    modeKao = "年齢と表情";
                    return List.of(new TextMessage("画像認識できませんでした😭"));
                }
            } else if (mode == 3) {
                // 初期化
                mode = 0;
                try {
                    // テキスト認識の時に対応する
                    TextResponse textResponse = textService.doTextninsiki(event);
                    listMessage = List.of(new TextReply(textResponse).reply());
                } catch (Exception e) {
                    return List.of(new TextMessage("画像認識できませんでした😭"));
                }
            } else if (mode == 4) {
                // 初期化
                mode = 0;
                try {
                    // 有名人認識の時に対応する
                    FamousResponse famousResponse = famousService.doFamous(event);
                    listMessage = List.of(new FamousReply(famousResponse).reply());
                } catch (Exception e) {
                    return List.of(new TextMessage("画像認識できませんでした😭"));
                }
            }

        return listMessage;
    }

}