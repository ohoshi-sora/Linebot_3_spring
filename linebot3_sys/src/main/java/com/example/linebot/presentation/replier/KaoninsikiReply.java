package com.example.linebot.presentation.replier;

import com.example.linebot.presentation.replier.Replier;
import com.example.linebot.service.KaoninsikiResponse;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KaoninsikiReply implements Replier {

    public static final String MESSAGE_FORMAT_NENREI = "この人は %d パーセントの確率で　%s で\n" +
            "年齢は %d ~ %d 才の間と推測しました😱";
    public static final String MESSAGE_FORMAT_HYOUJYOU = "この人は %d パーセントの確率で %s 感じです😎";
    public static final String MESSAGE_FORMAT_ALL = "この人は %d パーセントの確率で　%s で\n" +
            "年齢は %d ~ %d 才の間と推測しました😱\n" +
            "%d パーセントの確率で %s 感じです😎";

    public final KaoninsikiResponse kaoninsikiResponse;
    private String modeKao;

    public KaoninsikiReply(KaoninsikiResponse kaoninsikiResponse, String modeKao) {
        this.kaoninsikiResponse = kaoninsikiResponse;
        this.modeKao = modeKao;
    }

    private String genderToJap(String gender){
        String text;
        if (gender.equals("Male")) {
            text = "男性";
        } else {
            text = "女性";
        }
        return text;
    }


    @Override
    public Message reply(){
        int low  = kaoninsikiResponse.low();
        int high = kaoninsikiResponse.high();
        String gender = genderToJap(kaoninsikiResponse.gender());
        int genderCon = (int)(kaoninsikiResponse.gender_con());

        // 表情をListに入れる
        List<Integer> emotion = new ArrayList<>();
        emotion.add((int)kaoninsikiResponse.calm());    // 0
        emotion.add((int)kaoninsikiResponse.confused());    // 1
        emotion.add((int)kaoninsikiResponse.surprised());   // 2
        emotion.add((int)kaoninsikiResponse.fear());     // 3
        emotion.add((int)kaoninsikiResponse.angry());   // 4
        emotion.add((int)kaoninsikiResponse.disgusted());   // 5
        emotion.add((int)kaoninsikiResponse.happy());   // 6

        // 確率が一番大きい表情をoutEmoに入れる
        int outEmo = Collections.max(emotion);
        String outEmoStr = "";

        // 確率が大きい表情を文章化している
        for (int i = 0; i < 7; i++) {
            if (emotion.get(i) == outEmo) {
                switch (i) {
                    case 0 -> outEmoStr = "落ち着いている";
                    case 1 -> outEmoStr = "困惑している";
                    case 2 -> outEmoStr = "驚いている";
                    case 3 -> outEmoStr = "怖がっている";
                    case 4 -> outEmoStr = "怒っている";
                    case 5 -> outEmoStr = "うんざりしている";
                    case 6 -> outEmoStr = "喜んでいる";
                }
            }
        }

        String message = "";

        // modeKaoに合わせた表示
        if (modeKao.equals("年齢")) {
            System.out.println(genderCon);
            System.out.println(kaoninsikiResponse.gender_con());
            message = String.format(MESSAGE_FORMAT_NENREI, genderCon, gender, low, high);
        } else if (modeKao.equals("表情")) {
            message = String.format(MESSAGE_FORMAT_HYOUJYOU, outEmo, outEmoStr);
        } else{
            message = String.format(MESSAGE_FORMAT_ALL, genderCon, gender, low, high, outEmo, outEmoStr);
        }
        return new TextMessage(message);
    }
}
