package com.example.bagunic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naylalabs.semiradialmenu.RadialMenuView;

public class MainActivity extends AppCompatActivity {
    private Fragment playListFragment, songFragment, artistFragment, albumsFragment, folderFragment;
    RadialMenuView radialMenuView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton button = findViewById(R.id.mapbutton);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        BottomNavigationView bottomView = findViewById(R.id.bottomNavigationView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        bottomView.setOnNavigationItemSelectedListener(listener);

        playListFragment = new Fragment1();
        songFragment = new Fragment2();
        artistFragment = new Fragment3();
//        albumsFragment = new AlbumsFragment();
//        folderFragment = new FolderFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, playListFragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, playListFragment).commit();
                    return true;
                case R.id.item2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, songFragment).commit();
                    return true;
                case R.id.item3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, artistFragment).commit();
                    return true;
                case R.id.item4:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, albumsFragment).commit();
                    return true;
//                case R.id.menu5:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, folderFragment).commit();
//                    return true;
            }
            return false;
        }
    };
}