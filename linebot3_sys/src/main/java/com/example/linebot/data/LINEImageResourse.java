package com.example.linebot.data;

import org.springframework.core.io.ByteArrayResource;

public class LINEImageResourse extends ByteArrayResource{

    public LINEImageResourse(byte[] byteArray){
        super(byteArray);
    }

    @Override
    public String getFilename(){
        // LINEBOTを通すと、LINE側で全ての画像がJPEGに変換される
        // そのため、元のファイル名と異なる仮のファイル名を設定する
        return "image.jpg";
    }

}
