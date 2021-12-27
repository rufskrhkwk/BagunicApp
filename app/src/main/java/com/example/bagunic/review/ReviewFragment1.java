package com.example.bagunic.review;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagunic.MainActivityL;
import com.example.bagunic.R;

import java.util.ArrayList;
import java.util.Date;

import lib.kingja.switchbutton.SwitchMultiButton;


/**
 * 날씨 아이콘
 *
 * ① 맑음
 * ② 구름 조금
 * ③ 구름 많음
 * ④ 흐림
 * ⑤ 비
 * ⑥ 눈/비
 * ⑦ 눈
 */
public class ReviewFragment1 extends Fragment {
    private static final String TAG = "Fragment1";

    RecyclerView recyclerView;
    NoteAdapter adapter;
    ImageView reviewhome;
    Context context;
    com.example.bagunic.review.OnTabItemSelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof com.example.bagunic.review.OnTabItemSelectedListener) {
            listener = (com.example.bagunic.review.OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.reviewfragment1, container, false);
        reviewhome = rootView.findViewById(R.id.reviewhome);
        reviewhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivityL.class);
                startActivity(intent);
            }
        });
        initUI(rootView);

        // 데이터 로딩
        loadNoteListData();

        return rootView;
    }


    private void initUI(ViewGroup rootView) {

        Button todayWriteButton = rootView.findViewById(R.id.todayWriteButton);
        todayWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTabSelected(1);
                }
            }
        });

        SwitchMultiButton switchButton = rootView.findViewById(R.id.switchButton);
        switchButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(getContext(), tabText, Toast.LENGTH_SHORT).show();

                adapter.switchLayout(position);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        adapter = new NoteAdapter();

        adapter.addItem(new com.example.bagunic.review.Note(0, "0", "강남구 삼성동", "", "","오늘 너무 행복해!", "0", null, "2월 10일"));
        adapter.addItem(new com.example.bagunic.review.Note(1, "1", "강남구 삼성동", "", "","친구와 재미있게 놀았어", "0", null, "2월 11일"));
        adapter.addItem(new com.example.bagunic.review.Note(2, "0", "강남구 역삼동", "", "","집에 왔는데 너무 피곤해 ㅠㅠ", "0", null, "2월 13일"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnNoteItemClickListener() {
            @Override
            public void onItemClick(NoteAdapter.ViewHolder holder, View view, int position) {
                com.example.bagunic.review.Note item = adapter.getItem(position);

              //  Log.d(TAG, "아이템 선택됨 : " + item.get_id());

                if (listener != null) {
                    listener.showFragment2(item);
                }
            }
        });

    }

    /**
     * 리스트 데이터 로딩
     */
    @SuppressLint("Range")
    public int loadNoteListData() {
        com.example.bagunic.review.AppConstants.println("loadNoteListData called.");

        String sql = "select _id, WEATHER, ADDRESS, LOCATION_X, LOCATION_Y, CONTENTS, MOOD, PICTURE, CREATE_DATE, MODIFY_DATE from " + com.example.bagunic.review.NoteDatabase.TABLE_NOTE + " order by CREATE_DATE desc";

        int recordCount = -1;
        com.example.bagunic.review.NoteDatabase database = com.example.bagunic.review.NoteDatabase.getInstance(context);
        if (database != null) {
            Cursor outCursor = database.rawQuery(sql);

            recordCount = outCursor.getCount();
            com.example.bagunic.review.AppConstants.println("record count : " + recordCount + "\n");

            ArrayList<com.example.bagunic.review.Note> items = new ArrayList<com.example.bagunic.review.Note>();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String weather = outCursor.getString(1);
                String address = outCursor.getString(2);
                String locationX = outCursor.getString(3);
                String locationY = outCursor.getString(4);
                String contents = outCursor.getString(5);
                String mood = outCursor.getString(6);
                String picture = outCursor.getString(7);
                String dateStr = outCursor.getString(8);
                String createDateStr = null;
                if (dateStr != null && dateStr.length() > 10) {
                    try {
                        Date inDate = com.example.bagunic.review.AppConstants.dateFormat4.parse(dateStr);
                        createDateStr = com.example.bagunic.review.AppConstants.dateFormat3.format(inDate);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    createDateStr = "";
                }

                com.example.bagunic.review.AppConstants.println("#" + i + " -> " + _id + ", " + weather + ", " +
                        address + ", " + locationX + ", " + locationY + ", " + contents + ", " +
                        mood + ", " + picture + ", " + createDateStr);

                items.add(new com.example.bagunic.review.Note(_id, weather, address, locationX, locationY, contents, mood, picture, createDateStr));
            }

            outCursor.close();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();

        }

        return recordCount;
    }

}