package com.example.linebot.presentation.replier;

import com.example.linebot.service.FamousResponse;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class FamousReply implements Replier {

    public static final String MESSAGE_FORMAT = "この人は %d パーセントの確率で %s です！！！";

    private final FamousResponse famousResponse;

    public FamousReply(FamousResponse famousResponse) {
        this.famousResponse = famousResponse;
    }

    @Override
    public Message reply() {
        String name = famousResponse.name();
        int conf = (int)(famousResponse.conf());

        String message = String.format(MESSAGE_FORMAT,conf, name);
        return new TextMessage(message);
    }

}
