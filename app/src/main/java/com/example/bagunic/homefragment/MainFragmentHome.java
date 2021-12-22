package com.example.bagunic.homefragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bagunic.startfragment.StartFragment_1;
import com.example.bagunic.startfragment.StartFragment_2;
import com.example.bagunic.startfragment.StartFragment_3;


public class MainFragmentHome extends FragmentStateAdapter {

    public int mCount;
    public MainFragmentHome(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        if(index==0) return new HomeFragment();
        else if(index==1) return new HomeFragment2();
        else return new HomeFragment3();
    }
    @Override
    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition(int position) { return position % mCount; }
}