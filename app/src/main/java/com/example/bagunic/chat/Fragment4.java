package com.example.bagunic.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bagunic.R;
import com.example.bagunic.chat.Chat;
import com.example.bagunic.chat.ChatAdapter;
import com.example.bagunic.log.BagunicMemberVO;


public class Fragment4 extends Fragment {
    BagunicMemberVO and;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.mainfragment4, container, false);
        Intent intent = getActivity().getIntent();

        Bundle extra = getArguments();
        if(extra != null){
            and = (BagunicMemberVO)extra.getSerializable("and");
        }
        Button gochat = rootView.findViewById(R.id.gochat);

        gochat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getLoginID();
                Log.d("test", "onClick: "+id);
                Intent intent = new Intent(getActivity(), Chat.class);
                //BagunicMemberVO and = (BagunicMemberVO) intent.getSerializableExtra("and");
                intent.putExtra("and",and);
                startActivity(intent);
            }
        });


        return rootView;
    }
    // 불러오는 코드
    public String getLoginID(){
        return getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE).getString("LoginID", "아이디정보 없음");
    }
}