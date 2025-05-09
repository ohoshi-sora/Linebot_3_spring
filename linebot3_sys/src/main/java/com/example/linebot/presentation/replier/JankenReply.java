package com.example.linebot.presentation.replier;

import com.example.linebot.service.JankenResult;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class JankenReply implements Replier {

    public static final String MESSAGE_FORMAT = "あなた：%s, 相手：%s\n結果：%s";

    private final JankenResult jankenResult;

    public JankenReply(JankenResult jankenResult) {
        this.jankenResult = jankenResult;

    }

    private String intToJap(int hito){
        if (hito == 0) {
            return "グー";
        } else if (hito == 1) {
            return "チョキ";
        } else if (hito == 2){
            return "パー";
        } else {
            return "エラー";
        }
    }

    private String syouhai(int kekka){
        if (kekka == 0) {
            return "引き分け";
        } else if (kekka == 1) {
            return "負け";
        } else if (kekka == 2){
            return "勝ち";
        } else {
            return "エラー";
        }
    }


    @Override
    public Message reply() {
        String jibun = intToJap(jankenResult.response().jibun());
        String aite = intToJap(jankenResult.response().aite());
        String kekka = syouhai(jankenResult.response().kekka());

        String message = String.format(MESSAGE_FORMAT, jibun, aite, kekka);
        return new TextMessage(message);
    }
}
