package com.example.linebot.presentation.replier;

import com.example.linebot.service.Senreki;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class SenrekiReply implements Replier{

    public static final String MESSAGE_FORMAT  ="あなたは %d 戦中 %d 勝 (勝率 %d パーセント) です。";

    public final Senreki senreki;


    public SenrekiReply(Senreki senreki) {
        this.senreki = senreki;
    }

    @Override
    public Message reply() {
        int gameCount = senreki.gameCount();
        int jibunWinCount = senreki.jibunWinCount();
        int jibunWinRate = (int)(senreki.jibunWinRate() * 100);

        String message = String.format(MESSAGE_FORMAT, gameCount, jibunWinCount, jibunWinRate);
        return new TextMessage(message);
    }
}
