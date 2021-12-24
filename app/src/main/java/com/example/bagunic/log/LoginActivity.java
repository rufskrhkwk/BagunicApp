package com.example.bagunic.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.bagunic.MainActivityL;
import com.example.bagunic.R;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView join;
    private EditText edtidlog, edtpwlog;
    private Button btnlog, btnlogreset,escape;
    private ImageButton kakaobutton;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String TAG = "main";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_login);
        edtidlog = findViewById(R.id.edtid);
        edtpwlog = findViewById(R.id.edtpw);
        btnlog = findViewById(R.id.btnlogin);
        join = findViewById(R.id.join);
        escape = findViewById(R.id.escape);
        kakaobutton = findViewById(R.id.kakaobutton);
        //getSharedPreferences("파일이름",'모드')
        //모드 => 0 (읽기,쓰기가능)
        //모드 => MODE_PRIVATE (이 앱에서만 사용가능)

  escape.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent intent = new Intent(getApplicationContext(),MainActivityL.class);
          startActivity(intent);
      }
  });
//        Log.d("KeyHash", getKeyHash());

        kakaobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    login();
                } else {
                    accountLogin();
                }
            }

        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }

        });


    }

    public void login() {
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();


            }
            return null;
        });
    }

    public void accountLogin() {
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(getApplicationContext(), (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();

            }
            return null;
        });
    }

    public void getUserInfo() {
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                System.out.println("로그인 완료");
                Log.i(TAG, user.toString());
                {
                    Log.i(TAG, "사용자 정보 요청 성공" +

                            "\n이메일: " + user.getKakaoAccount().getEmail());
                }
                Account user1 = user.getKakaoAccount();
                // String user2 = user.getKakaoAccount().getpro();
                System.out.println("사용자 계정" + user1);
                //System.out.println("사용자 이름" + user2);
                System.out.println("이봐요 여긴 어디죠" + "/" + user1.getProfile());
                KaKaoRequest(user1);
            }


            return null;
        });

        Intent intent = new Intent(LoginActivity.this, MainActivityL.class);

        startActivity(intent);

    }


    public void sendRequest() {
        // Volley Lib 새로운 요청객체 생성
        queue = Volley.newRequestQueue(this);
        // 서버에 요청할 주소
//        String url = "http://210.183.87.46:8086/z_Happynic/Login";
        String url = "http://59.0.129.177:8087/BaguNic_project/LoginService";


        // 요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
                if (response.equals("로그인 시도가 있었읍니다")) {
                    Toast.makeText(getApplicationContext(), "로그인실패", Toast.LENGTH_SHORT).show();
                } else {
                    //json타입 문자열을 json객체로 변환해주는 메소드
                    try {
                        JSONObject json = new JSONObject(response);
                        String id = json.getString("email");
                        String phone = json.getString("phone");

                        BagunicMemberVO and = new BagunicMemberVO(id, phone);
                        Intent intent = new Intent(LoginActivity.this, MainActivityL.class);
                        Log.v("resultValue", id + "/" + phone + "/");
                        successLogin(id);
                        intent.putExtra("and", and);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Bagunic에 잘 오셨어요!", Toast.LENGTH_SHORT).show();
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
                params.put("email", edtidlog.getText().toString());
                params.put("pw", edtpwlog.getText().toString());
                return params;
            }
        };
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

    public void KaKaoRequest(Account user1) {
        // Volley Lib 새로운 요청객체 생성


        queue = Volley.newRequestQueue(this);
        // 서버에 요청할 주소
//        String url = "http://210.183.87.46:8086/z_Happynic/Login";
        String url = " http://59.0.129.177:8087/BaguNic_project/kakaoLogin";


        // 요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
                if (response.equals("로그인 시도가 있었읍니다")) {
                    Toast.makeText(getApplicationContext(), "로그인실패", Toast.LENGTH_SHORT).show();
                } else {
                    //json타입 문자열을 json객체로 변환해주는 메소드
                    try {
                        JSONObject json = new JSONObject(response);
                        String id = json.getString("email");
                        String nick = json.getString("nick");
                        BagunicMemberVO and = new BagunicMemberVO(id, nick);
                        Log.v("resultValue", id + "/" + nick);
                        successLogin(id);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplicationContext().startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Bagunic에 잘 오셨어요!", Toast.LENGTH_SHORT).show();
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
                params.put("email", user1.getEmail());
                params.put("name", user1.getProfile().getNickname());
                return params;
            }
        };
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }


    public void successLogin(String id) {
        SharedPreferences spf = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        spf.edit().putString("LoginID", id).commit();
    }


    // 키해시 얻기
//    public String getKeyHash() {
//        try {
//            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            if (packageInfo == null) return null;
//            for (Signature signature : packageInfo.signatures) {
//                try {
//                    MessageDigest md = MessageDigest.getInstance("SHA");
//                    md.update(signature.toByteArray());
//                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
//                } catch (NoSuchAlgorithmException e) {
//                    Log.w("getKeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//                }
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.w("getPackageInfo", "Unable to getPackageInfo");
//        }
//        return null;
//    }

}