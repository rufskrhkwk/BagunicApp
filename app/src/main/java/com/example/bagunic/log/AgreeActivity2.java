package com.example.bagunic.log;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bagunic.R;

public class AgreeActivity2 extends AppCompatActivity {
    ScrollView scrolview3,scrolview34;
    TextView sc3,sc4;
    Button agreebutton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree2);
        Intent intent =getIntent();
        scrolview3 = findViewById(R.id.scrolview3);
        scrolview34 = findViewById(R.id.scrolview4);
        sc3 = findViewById(R.id.sc3);
        sc4 = findViewById(R.id.sc4);
        agreebutton2 = findViewById(R.id.agreebutton2);
        agreebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        String aa = "1. 제품이용 후 물품정리하여 반납해주세요\n" +
                "2. 대여하였던 장소에 반납해주세요\n" +
                "3. Bagunic 제품 이용전과 이용후 꼭 인증사진을 올려주세요\n" +
                "4. 제픔 이용중 고장시 바로 고객센터로 연락해주세요\n" +
                "5. 임의로 제품 손상시 형사처벌의 대상이 될수 있습니다.\n" +
                "6. 추가요금 미납 시 Bagunic 이용을 제한합니다.\n" +
                "7. 제품에 물기가 닿으면 배터리 손상에 문제가 될수 있습니다.\n" +
                "8. 기타문의 사항은 고객센터로 연락바랍니다.";

        sc3.setText(aa);

        String bb = "기본 2시간 10,000원\n" +
                "※ 1회 대여 후 2시간 이상 이용자에게 시간에 따라 추가 사용료가 발생합니다.\n" +
                "※ 추가요금 30분당 2,000원\n" +
                "※다양한 이벤트를 통해 할인정보를 받아가세요\n" +
                "※리뷰 작성시 한주간 하트를 많이 받으신분에게 선물이 있습니다.\n" +
                "※근거리 채팅을 통해 주변 사람들과 소통해보세요\n" +
                "※블루투스 연결로 제픔의 다양한 기능을 사용 하실수 있습니다.\n";
        sc4.setText(bb);

    }
}