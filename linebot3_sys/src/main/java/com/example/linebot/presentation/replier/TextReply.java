package com.example.linebot.presentation.replier;

import com.example.linebot.service.TextResponse;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class TextReply implements Replier{

    public static final String MESSAGE_FORMAT_TEXT = "%s と読み取りました！";

    public final TextResponse textResponse;

    public TextReply(TextResponse textResponse) {
        this.textResponse = textResponse;
    }

    public Message reply(){
        String text = textResponse.text();
        String message = String.format(MESSAGE_FORMAT_TEXT, text);
        return new TextMessage(message);
    }
}
