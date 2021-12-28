package com.example.bagunic.notice;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagunic.R;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<com.example.bagunic.notice.NoticeVersions> noticeVersionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        setContentView(R.layout.activity_notice);

        recyclerView = findViewById(R.id.recycle);

        initData();
        setRecyclerView();

    }
    private void setRecyclerView(){
        com.example.bagunic.notice.NoticeAdapter noticeAdapter = new com.example.bagunic.notice.NoticeAdapter(noticeVersionsList);
        recyclerView.setAdapter(noticeAdapter);
        recyclerView.setHasFixedSize(true);
    }
    private void initData(){
        noticeVersionsList = new ArrayList<>();

        noticeVersionsList.add(new com.example.bagunic.notice.NoticeVersions("BaguNic 서비스 정기점검 안내(12.18 AM 3:00~7:50",
                "2021.12.18","관리자",
                "안녕하세요 BaguNic 입니다. 더욱 안정적인 서비스 제공을 위한 정기 점검을 안내드립니다.\n"+
                        "점검일시 안내\n" +
                        "▶ 2021-12-18(토) AM 3:00~7:50(4시간 50분\n" +
                        "점검범위 안내\n" +
                        "▶ BaguNic 앱, BaguNic 서비스 전체 사용불가\n" +
                        "서비스 이용에 불편을 드려 죄송합니다.\n" +
                        "빠른 점검과 안정된 서비스로 찾아뵙겠습니다.\n" +
                        "감사합니다."));
        noticeVersionsList.add(new com.example.bagunic.notice.NoticeVersions("BaguNic 개인정보처리방침 개정안내",
                "2021.12.18","관리자",
                "개인정보 삭제 예정"));
        noticeVersionsList.add(new com.example.bagunic.notice.NoticeVersions("BaguNic 고객센터이용지연안내",
                "2021.12.18","관리자",
                "이벤트 준비중"));
        noticeVersionsList.add(new com.example.bagunic.notice.NoticeVersions("BaguNic 개인정보처리방침 개정안내",
                "2021.12.18","관리자",
                ""));

    }

}