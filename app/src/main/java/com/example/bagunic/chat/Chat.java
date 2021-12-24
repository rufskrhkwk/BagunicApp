package com.example.bagunic.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bagunic.R;
import com.example.bagunic.log.BagunicMemberVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    private EditText edtchat;
    private Button btnchat2;
    private ListView listchat;
    private BagunicMemberVO and;
    private ChatAdapter adapter;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        edtchat = findViewById(R.id.edtchat);
        btnchat2 = findViewById(R.id.btnchat2);
        listchat = findViewById(R.id.listchat);
        Intent intent = getIntent();
        and = (BagunicMemberVO) intent.getSerializableExtra("and");
        edtchat.setText(and.getId());
        getLoginID();


        btnchat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭시 작동해야되는 기능
                //1. chatinputservice라는 servlet로 닉네임과 채팅내용을 전송한다.
                sendRequest();

            }
        });
        Thread thread = new Thread(new ChatThread());
        thread.start();


    }

    public class ChatThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                getChat();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void getChat() {

        // Volley Lib 새로운 요청객체 생성
        queue = Volley.newRequestQueue(this);
        // 서버에 요청할 주소
        String url = "http://59.0.129.177:8087/BaguNic_project/ChatList";
        // 요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
                adapter = new ChatAdapter();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String nick = jsonObject.getString("email");
                        String chat = jsonObject.getString("content");
                        String day = jsonObject.getString("day");

                        Log.v("resultValue", nick + "/" + chat + "/" + day + "/");

                        adapter.additem(nick, chat, day);
                    }
                    listchat.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            // 보낼 데이터를 저장하는 곳
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        stringRequest.setTag(TAG);
        queue.add(stringRequest);


    }


    public void sendRequest() {
        // Volley Lib 새로운 요청객체 생성
        queue = Volley.newRequestQueue(this);
        // 서버에 요청할 주소
        //  String url = "http://121.147.52.134:8087/AndroidServer/chatinputservice";
        String url = "http://59.0.129.177:8087/BaguNic_project/ChatInputService";
        // 요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
                edtchat.setText("");

            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            // 보낼 데이터를 저장하는 곳
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                Log.d("출력좀요",and.getId());
                params.put("email", and.getId());
                params.put("content", edtchat.getText().toString());

                return params;
            }
        };
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

    // 불러오는 코드
    public String getLoginID(){
        return getApplicationContext().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE).getString("LoginID", "아이디정보 없음");
    }
}