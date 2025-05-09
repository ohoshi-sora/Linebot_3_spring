package com.example.linebot.presentation.replier;

import com.example.linebot.service.FamousCountResponse;
import com.example.linebot.service.FamousResponse;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.List;

public class FamousCountReply implements Replier {
    public  static final String MESSAGE_FORMAT = "信頼度が高いTop5\n" +
            "1位：%s：%.4fパーセント\n2位：%s：%.4fパーセント\n" +
            "3位：%s：%.4fパーセント\n4位：%s：%.4fパーセント\n" +
            "5位：%s：%.4fパーセント";

    public final List<FamousResponse> ranking;

    public FamousCountReply(List<FamousResponse> ranking) {
        this.ranking = ranking;
    }

    @Override
    public Message reply() {
        String message = String.format(MESSAGE_FORMAT,
                ranking.get(0).name(),ranking.get(0).conf(),
                ranking.get(1).name(),ranking.get(1).conf(),
                ranking.get(2).name(),ranking.get(2).conf(),
                ranking.get(3).name(),ranking.get(3).conf(),
                ranking.get(4).name(),ranking.get(4).conf());
        return new TextMessage(message);
    }
}
