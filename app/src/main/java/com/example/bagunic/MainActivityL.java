package com.example.bagunic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bagunic.chat.Fragment4;
import com.example.bagunic.homefragment.Fragment1;
import com.example.bagunic.review.ReviewActivity;
import com.example.bagunic.sellbagunic.Fragment2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naylalabs.semiradialmenu.MenuItemView;
import com.naylalabs.semiradialmenu.RadialMenuView;

import java.util.ArrayList;

public class MainActivityL extends AppCompatActivity implements RadialMenuView.RadialMenuListener {
    private Fragment BasketFragment, HomeFragment, SettingFragment, ChatFragment, ReviewFragment;
    RadialMenuView radialMenuView;
    Button radialbutton;
    ImageButton mapbutton;
    TextView loginid;
    ImageView logo;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainl);
        Intent intent = getIntent();
        logo = findViewById(R.id.logo);
        loginid = findViewById(R.id.loginid);
        mapbutton = findViewById(R.id.mapbutton);

//        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        BottomNavigationView bottomView = findViewById(R.id.bottomNavigationView);

//        BagunicMemberVO member = (BagunicMemberVO) intent.getSerializableExtra("and");
//        loginid.setText(member.getId() + "님 환영해요");
//        loginid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivityL.class);
                startActivity(home);
            }
        });

        radialMenuView = findViewById(R.id.radial_menu_view);
        radialbutton = findViewById(R.id.radialbutton);
        MenuItemView itemOne = new MenuItemView(this, "대여/반납", R.drawable.baseline_flip_camera_android_black_24, R.color.orange);
        MenuItemView itemTwo = new MenuItemView(this, "LED", R.drawable.baseline_wb_twilight_black_24, R.color.green);
        MenuItemView itemThree = new MenuItemView(this, "바구니 잠금", R.drawable.baseline_lock_black_24, R.color.vividPurple);
//      MenuItemView itemFour = new MenuItemView(this,"바구니 잠금", R.drawable.ic_blog_white, R.color.darkRed);
//      MenuItemView itemFive = new MenuItemView(this, "Selam", R.drawable.ic_profile_white, R.color.darkGreen2);
        ArrayList<MenuItemView> items = new ArrayList<>();
        items.add(itemOne);
        items.add(itemTwo);
        items.add(itemThree);
//        items.add(itemFour);
//        items.add(itemFive);
        radialMenuView.setListener(this).setMenuItems(items).setCenterView(radialbutton).setInnerCircle(true, R.color.white).setOffset(10).build();

        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        bottomView.setOnNavigationItemSelectedListener(listener);

        HomeFragment = new Fragment1();
        BasketFragment = new Fragment2();
        SettingFragment = new Fragment3();
        ChatFragment = new Fragment4();
        ReviewFragment = new Fragment5();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, HomeFragment).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mainhome:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, HomeFragment).commit();
                    return true;
                case R.id.mainbasket:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, BasketFragment).commit();
                    return true;
                case R.id.mainsetting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, SettingFragment).commit();
                    return true;
                case R.id.mainchat:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("and", getIntent().getSerializableExtra("and"));
                    ChatFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ChatFragment).commit();
                    return true;
                case R.id.mainreview:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ReviewFragment).commit();
                    return true;
            }
            return false;
        }
    };

    public void showClose(View view) {
        radialMenuView.show();
    }

    @Override
    public void onItemClicked(int i) {

        if (i == 0) {
            Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(intent);
            Toast.makeText(this, "반납/대여", Toast.LENGTH_SHORT).show();
        } else if (i == 1) {
            sendLED();
            Toast.makeText(this, "LED on", Toast.LENGTH_SHORT).show();
        } else if (i == 2) {
            sendSol();
            Toast.makeText(this, "Lock on", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSol() {
        queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://192.168.5.46:5000/sol";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

        };
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }


    public void sendLED() {

        queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://192.168.5.46:5000/led";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

        };
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

}