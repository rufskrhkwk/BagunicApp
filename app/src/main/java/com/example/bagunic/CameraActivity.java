package com.example.bagunic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class CameraActivity extends AppCompatActivity {
    ImageView imageView = null;
    Button button = null;
    private final int REQ_CODE_SELECT_IMAGE = 100;
    private static final int CAMERA_CODE = 1002;
    private String img_path = new String();
    private String serverURL = "http://59.0.129.177:8087/BaguNic_project/getimg.jsp";  //<<서버주소
    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    private String imageName = null;
    Button btn_send, btn_camera,gpssend,start,end;
    private RequestQueue queue;
    private StringRequest stringRequest;
    Uri imageUri;
    String date;
    String TAG ="Last Camera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
       gpssend = findViewById(R.id.start);


        Intent intent = getIntent();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end();
            }
        });
        btn_camera = findViewById(R.id.btn_camera);
        imageView = (ImageView) findViewById(R.id.imageView);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                imageName = "SAMPLE_"+timeStamp+".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                imageUri = createImageUri(imageName, "jpeg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                launcher.launch(intent);
            }
        });
        //이미지를 띄울 위젯
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

            }
        });

        btn_send = findViewById(R.id.btn_send);
        //이미지 전송 버튼
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGps();
                sendGps();
                DoFileUpload(serverURL, img_path);
                Toast.makeText(getApplicationContext(), "이미지 전송 성공", Toast.LENGTH_SHORT).show();
                Log.d("Send", "Success");


            }
        });
    }//end of onCreate()


    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
          try{
              if (result.getResultCode() == RESULT_OK)
              {
                  Log.e(TAG, "result : " + result);
                  Intent intent = result.getData();
                  Log.e(TAG, "intent : " + intent);
                  Uri uri = intent.getData();
                  Log.e(TAG, "uri : " + uri);
                  // imageview.setImageURI(uri);
              }
          }catch (Exception e){
              e.printStackTrace();
          }



        }
    });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getBaseContext(), "사진을 등록해주세요.", Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
                    Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
                    //이미지를 비트맵형식으로 반환
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    //사용자 단말기의 width , height 값 반환
                    int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
                    int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());

                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                    image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                    ImageView image = (ImageView) findViewById(R.id.imageView);  //이미지를 띄울 위젯 ID값
                    image.setImageBitmap(image_bitmap_copy);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//end of onActivityResult()

    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        verifyStoragePermissions(this);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(CameraActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

        return imgPath;
    }//end of getImagePathToUri()

    public void DoFileUpload(String apiUrl, String absolutePath) {

        HttpFileUpload(apiUrl, "", absolutePath);

    }


    public void HttpFileUpload(String urlString, String params, String fileName) {

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {

            File sourceFile = new File(fileName);

            DataOutputStream dos;

            if (!sourceFile.isFile()) {

                Log.e("uploadFile", "Source File not exist :" + fileName);

            } else {

                FileInputStream mFileInputStream = new FileInputStream(sourceFile);
                URL connectUrl = new URL(urlString);
                // open connection
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                // write data
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                mFileInputStream.close();
                dos.flush(); // finish upload...

                if (conn.getResponseCode() == 200) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                }

                mFileInputStream.close();
                dos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }




    public void getGps() {

        queue = Volley.newRequestQueue(this);
        String url = "http://192.168.5.46:5000/gps";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
                if (response.equals("로그인 시도가 있었읍니다")) {

                } else {
                    //json타입 문자열을 json객체로 변환해주는 메소드
                    try {
                        JSONObject json = new JSONObject(response);
                        Log.d("키값", json.toString());
                        String gps = json.getString("gps");
                        GpsVO log = new GpsVO(gps);
                        Log.v("resultValue", gps);
                        //Log.v("resultValue",json.getString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
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

    private void sendGps() {
        queue = Volley.newRequestQueue(this);
        String url = "http://59.0.129.177:8087/BaguNic_project/gps";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
                if (response.equals("로그인 시도가 있었읍니다")) {
                  } else {
                    //json타입 문자열을 json객체로 변환해주는 메소드
                    try {
                        JSONObject json = new JSONObject(response);
                        Log.d("키값", json.toString());
                        String gps = json.getString("gps");
                        GpsVO log = new GpsVO(gps);
                        Log.v("resultValue", gps);
                        //Log.v("resultValue",json.getString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
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
    public void start() {

        queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://192.168.5.46:5000/Rental";

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
        Toast.makeText(getApplicationContext(), "시작하죠!", Toast.LENGTH_SHORT).show();
    }
    public void end() {

        queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://192.168.5.46:5000/Return";

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
        Toast.makeText(getApplicationContext(), "고맙습니다!", Toast.LENGTH_SHORT).show();
    }
    private Uri createImageUri(String fileName, String mimeType) {
        Random random = new Random();
        date = Integer.toString(random.nextInt(1000));

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample"+date+".jpg"); // 확장자가 붙어있는 파일명 ex) sample.jpg
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType); // ex) image/jpeg
        ContentResolver contentResolver = getContentResolver();

        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }


} //end of class