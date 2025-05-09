package com.example.linebot.service;

import com.example.linebot.data.FamousLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

@Service
public class FamousCountService {

    private FamousLog famousLog;

    public FamousCountService(FamousLog famousLog) {
        this.famousLog = famousLog;
    }

    public List<FamousResponse> calcFamousCount () {

        FamousResponse nul= new FamousResponse("なし", 0);

        // データベースを降順に並び替える
        List<FamousResponse> famousResponses = famousLog.confDesc();

        // データベースからリストを取得
//        List<FamousResponse> famousResponses = famousLog.selectDistinct();
        List<FamousResponse> ranking = new ArrayList<>();

        ranking.add(famousResponses.get(0));
        // データがない場合に対応
        for (int i = 1; i < 5; i++) {
            try {
                if(famousResponses.get(i) != null &&
                        famousResponses.get(i-1) != famousResponses.get(i)) {
                    ranking.add(famousResponses.get(i));
                }else {
                    ranking.add(nul);
                }
            } catch (Exception e) {
                ranking.add(nul);
            }


        }

        return ranking;
    }
}







//    public List<FamousCountResponse> calcFamousCount () {
//
//        FamousCountResponse nul= new FamousCountResponse("なし", 0);
//
//        // データベースを降順に並び替える
//        famousLog.confDesc();
//
//        // データベースからリストを取得
//        List<FamousResponse> famousResponses = famousLog.selectALL();
//
//
//        // 毎回famous_count_logからリストを取得
//        List<FamousCountResponse> famousCountResponses = famousCountLog.selectALL();
//
//        System.out.println(famousCountResponses);
//        if (famousCountResponses == null || famousCountResponses.isEmpty()) {
//            famousCountLog.insert(famousResponses.get(0).name());
//            System.out.println("00000");
//        }
//
//
//        // 同じ名前がなかったら、insert
//        for (int i = 1; i < famousResponses.size(); i++) {
//
//            // 毎回famous_count_logからリストを取得
//            famousCountResponses = famousCountLog.selectALL();
//
//            System.out.println(famousResponses.get(i).name());
//
//            for (int j = 0; j < famousCountResponses.size(); j++){
//                if (Objects.equals(famousResponses.get(i).name(), famousCountResponses.get(j).name())) {
//                    famousCountLog.update(famousResponses.get(i).name());
//                    System.out.println("1111111");
//                    break;
//                } else {
//                    famousCountLog.insert(famousResponses.get(i).name());
//                    System.out.println("222222222");
//                    break;
//                }
//            }
//            System.out.println("348598273985792");
//        }
//
//        // 降順に並び替え
//        famousCountLog.confDesc();
//
//        // リストを取得
//        famousCountResponses = famousCountLog.selectALL();
//
//        return famousCountResponses ;
//    }


