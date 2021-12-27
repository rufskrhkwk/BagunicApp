package com.example.bagunic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.bagunic.homefragment.Fragment1;
import com.example.bagunic.log.AgreeActivity;
import com.example.bagunic.sellbagunic.Fragment2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    private Fragment playListFragment, songFragment, artistFragment, albumsFragment, folderFragment;
    ImageButton mapbutton;
    TextView loginid;
    ImageView logo;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        logo = findViewById(R.id.logo);
        loginid = findViewById(R.id.loginid);
        mapbutton = findViewById(R.id.mapbutton);
//        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        BottomNavigationView bottomView = findViewById(R.id.bottomNavigationView);
        getLoginID();

        loginid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AgreeActivity.class);
                startActivity(intent);
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });

//        radialMenuView = findViewById(R.id.radial_menu_view);
//        radialbutton = findViewById(R.id.radialbutton);
//        MenuItemView itemOne = new MenuItemView(this, "대여/반납", R.drawable.baseline_flip_camera_android_black_24, R.color.orange);
//        MenuItemView itemTwo = new MenuItemView(this, "LED", R.drawable.baseline_wb_twilight_black_24, R.color.green);
//        MenuItemView itemThree = new MenuItemView(this, "바구니 잠금", R.drawable.baseline_lock_black_24, R.color.vividPurple);
//      MenuItemView itemFour = new MenuItemView(this,"바구니 잠금", R.drawable.ic_blog_white, R.color.darkRed);
//      MenuItemView itemFive = new MenuItemView(this, "Selam", R.drawable.ic_profile_white, R.color.darkGreen2);
//        ArrayList<MenuItemView> items = new ArrayList<>();
//        items.add(itemOne);
//        items.add(itemTwo);
//        items.add(itemThree);
//        items.add(itemFour);
//        items.add(itemFive);
//        radialMenuView.setListener(this).setMenuItems(items).setCenterView(radialbutton).setInnerCircle(true, R.color.white).setOffset(10).build();

        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        bottomView.setOnNavigationItemSelectedListener(listener);

        songFragment = new Fragment1();
        playListFragment = new Fragment2();
        artistFragment = new Fragment3();
//        albumsFragment = new Fragment4();
//        folderFragment = new FolderFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, songFragment).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mainhome:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, songFragment).commit();
                    return true;
                case R.id.mainbasket:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, playListFragment).commit();
                    return true;
                case R.id.mainsetting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, artistFragment).commit();
                    return true;
//                case R.id.mainchat:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, albumsFragment).commit();
//                    return true;
//                case R.id.menu5:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, folderFragment).commit();
//                    return true;
            }
            return false;
        }
    };

//    public void showClose(View view) {
//        radialMenuView.show();
//    }
//
//    @Override
//    public void onItemClicked(int i) {
//        Toast.makeText(this, "Item clicked - " + String.valueOf(i), Toast.LENGTH_SHORT).show();
//        if (i == 0) {
//            Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
//            startActivity(intent);
//        }else if(i == 1){
//            sendRequest();
//        }
//    }
//
//    public void sendRequest() {
//
//        queue = Volley.newRequestQueue(getApplicationContext());
//
//        String url = "http://220.80.203.121:5000/led";
//
//        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.v("resultValue",response);
//            }
//        }, new Response.ErrorListener() {
//            // 서버와의 연동 에러시 출력
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//
//        };
//        stringRequest.setTag(TAG);
//        queue.add(stringRequest);
//    }
public String getLoginID(){
    return getApplicationContext().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE).getString("LoginID", "아이디정보 없음");
}
}