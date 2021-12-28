package com.example.bagunic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import com.example.bagunic.log.LoginActivity;
import com.example.bagunic.notice.NoticeActivity;

public class Fragment3 extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.mainfragment3, container, false);

        // xml의 listview id를 반드시 "@android:id/list"로 해줘야 한다.
        String[] values = new String[] {"내 정보","공지사항","설정","테마","Q/A","고객센터"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        return rootView;
    }

    //아이템 클릭 이벤트
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        String strText = (String) l.getItemAtPosition(position);
        Log.d("Fragment: ", position + ": " +strText);
//        Toast.makeText(this.getContext(), "클릭: " + position +" " + strText, Toast.LENGTH_SHORT).show();
        if(position == 0){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else if(position == 1){
            Intent intent = new Intent(getActivity(), NoticeActivity.class);
            startActivity(intent);
        }
    }
}