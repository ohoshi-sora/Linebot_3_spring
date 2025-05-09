package com.example.linebot.data;

import com.example.linebot.service.FamousResponse;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FamousLog {

    // spring Frameworkのデータベース接続ライブラリを利用する
    private JdbcTemplate jdbcTemplate;

    // Spring Frameworkのデータベース接続ライブラリを初期化する
    public FamousLog(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // famous_logにデータを格納する
    public int insert(FamousResponse response) {
        String sql = "insert into famous_log VALUES (?, ?)";
        int n = jdbcTemplate.update(
                sql, response.name(), response.conf());
        return n;
    }

    // データベースの中身をFamousResponse型でreturnする
    public List<FamousResponse> selectALL() {
        String sql = "select name, conf from famous_log";
        List<FamousResponse> selected
                = jdbcTemplate.query(
                sql, new DataClassRowMapper<>(FamousResponse.class));
        return selected;
    }

    //　確率で降順に並び替えて重複をなくして取得
    public List<FamousResponse> confDesc() {
        String sql = "select distinct * from famous_log order by conf desc";
        List<FamousResponse> desc
                = jdbcTemplate.query(
                        sql, new DataClassRowMapper<>(FamousResponse.class));
        return desc;
    }


    public List<FamousResponse> selectDistinct() {
        String sql = "select distinct name, conf from famous_log";
        List<FamousResponse> selected
                = jdbcTemplate.query(
                sql, new DataClassRowMapper<>(FamousResponse.class));
        return selected;
    }


}