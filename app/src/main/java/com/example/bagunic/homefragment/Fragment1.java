package com.example.bagunic.homefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bagunic.R;

import me.relex.circleindicator.CircleIndicator3;


public class Fragment1 extends Fragment {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
    private CircleIndicator3 mIndicator;


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.mainfragment, container, false);

//ViewPager2
    mPager = rootView.findViewById(R.id.viewpagerhome);
//Adapter
    pagerAdapter = new MainFragmentHome(getActivity(), num_page);
    mPager.setAdapter(pagerAdapter);
//Indicator
    mIndicator = rootView.findViewById(R.id.indicatorhome);
    mIndicator.setViewPager(mPager);
    mIndicator.createIndicators(num_page,0);
//ViewPager Setting
    mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

    mPager.setCurrentItem(750); //시작 지점
    mPager.setOffscreenPageLimit(3); //최대 이미지 수
    mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            if (positionOffsetPixels == 0) {
                mPager.setCurrentItem(position);
            }
        }
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mIndicator.animatePageSelected(position%num_page);
        }
    });

    return rootView;
}
}