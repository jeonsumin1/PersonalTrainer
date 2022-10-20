package com.example.Personal_Trainer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private static String TAG = "phplogin";

    private static final String TAG_JSON = "user";
    private static final String TAG_ID = "userID";
    private static final String TAG_PASS = "userPassword";
    private static final String TAG_NAME = "userName";
    private static final String TAG_EMAIL = "userEmail";
    private static final String TAG_Gender = "userGender";
    private static final String TAG_Birth = "userBirth";
    private static final String TAG_HEIGHT = "userHeight";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;

    private EditText mEditTextID, mEditTextPass;
    private TextView btn_register;
    Button btn_login;
    private String mJsonString;
    private EditText et_id, et_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        //TextView loginText = (TextView) findViewById(R.id.loginText);
        //ImageView loginImage = (ImageView) findViewById(R.id.loginImage);
        //CheckBox rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
        //TextView forgotText = (TextView) findViewById(R.id.forgotText);

        //mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        //mListViewList = (ListView) findViewById(R.id.listView_main_list);
        mEditTextID = (EditText) findViewById(R.id.et_id);
        mEditTextPass = (EditText) findViewById(R.id.et_pass);

        mEditTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //Text 클릭 시 회원가입 화면으로 이동
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Signup_Php_Mysql.class);
                startActivity(intent);
            }
        });


        //login 버튼 클릭 시
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {



            public void onClick(View view) {

                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userID = et_id.getText().toString();
                String userPassword = et_pass.getText().toString();
                mArrayList.clear();

                // 빈칸 X, ID, Password 미입력시 로그인 X
                if(userID.equals("") || userPassword.equals("") ){
                    Toast.makeText(getApplicationContext(),"ID, Password를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                GetData task = new GetData();
                task.execute(mEditTextID.getText().toString(), mEditTextPass.getText().toString());
                Toast.makeText(LoginActivity.this, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
                // 로그인 하면서 사용자 정보 넘기기
                intent.putExtra("userID", userID);
                intent.putExtra("userPassword", userPassword);
                startActivity(intent);
                finish();

            }
        });


        mArrayList = new ArrayList<>();

    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
//        String errorString = null;  유민영

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행 다이얼로그 생성
            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, "Post response - " + result); // result 값 확인하기

            if (result == null) {
//
//                mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;
//                showResult(); 유민영
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String userID = (String) params[0];
            String userPassword = (String) params[1];

            String serverURL = "http://3.38.94.242/login.php";
            String postParameters = "&userID=" + userID + "&userPassword=" + userPassword;


            try { // HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송한다.
                URL url = new URL(serverURL); // 주소가 저장된 변수를 이곳에 입력한다.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);

                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();

                outputStream.write(postParameters.getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;

                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                    Log.d("php정상: ", "정상적으로 출력"); //로그 메세지로 정상적으로 출력을 찍는다.

                } else {
                    inputStream = httpURLConnection.getErrorStream();
                    Log.d("php비정상: ", "비정상적으로 출력"); // 로그 메세지로 비정상적으로 출력을 찍는다.

                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                Log.d("php 값 :", sb.toString());

                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
//    private void showResult(){ 유민영
//
//        try {
//            JSONObject jsonObject = new JSONObject(mJsonString);
//            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject item = jsonArray.getJSONObject(i);
//
//                String userID = item.getString(TAG_ID);
//                String userPassword = item.getString(TAG_PASS);
//                String userName = item.getString(TAG_NAME);
//                String userEmail = item.getString(TAG_EMAIL);
//                String userGender = item.getString(TAG_Gender);
//                String userBirth = item.getString(TAG_Birth);
//                String userHeight = item.getString(TAG_HEIGHT);
////                final String userSort = item.getString(TAG_SORT);
//
//                HashMap<String,String> hashMap = new HashMap<>();
//
//
//                hashMap.put(TAG_ID, userID);
//                hashMap.put(TAG_PASS, userPassword);
//                hashMap.put(TAG_NAME, userName);
//                hashMap.put(TAG_EMAIL, userEmail);
//                hashMap.put(TAG_Gender, userGender);
//                hashMap.put(TAG_Birth, userBirth);
//                hashMap.put(TAG_HEIGHT, userHeight);
//
//                mArrayList.add(hashMap);
//
//                Intent intent = new Intent(Login.this, MainActivity2.class); 유민영

//                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
//                dialog = builder.setMessage("로그인되었습니다.")
////                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                if(userSort.equals("2")) {
////                                    Intent intent = new Intent(LoginActivity.this, Home2Activity.class);
////                                    intent.putExtra("loginID", mEditTextID.getText().toString());
//////                                    intent.putExtra("loginSort", userSort);
////                                    startActivity(intent);
////                                }
////                                else if(userSort.equals("1")) {
////                                    Intent intent = new Intent(LoginActivity.this, Home3Activity.class);
////                                    intent.putExtra("loginID", mEditTextID.getText().toString());
////                                    intent.putExtra("loginSort", userSort);
////                                    startActivity(intent);
////                                }
//                            }
////                        }
//
//                        .create();
//                dialog.show();


//                return;   유민영
//            }


//            ListAdapter adapter = new SimpleAdapter(
//                    Login.this, mArrayList, R.layout.user_list,
//                    new String[]{TAG_ID,TAG_PASS,TAG_EMAIL,TAG_PHONE,TAG_SORT},
//                    new int[]{R.id.textView_list_id, R.id.textView_list_pass, R.id.textView_list_email,R.id.textView_list_phone,R.id.textView_list_sort}
//            );

//            mListViewList.setAdapter(adapter);

//        } catch (JSONException e) { 유민영
//
//            Log.d(TAG, "showResult : ", e);
//        }
//
//
//    }
//
//}

//    private EditText et_id, et_pass;
//    private TextView btn_register;
//    private Button btn_login;
//    private AlertDialog dialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        et_id = findViewById(R.id.et_id);
//        et_pass = findViewById(R.id.et_pass);
//        btn_login = findViewById(R.id.btn_login);
//        btn_register = findViewById(R.id.btn_register);
//
//        // 로그인 버튼 클릭
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
//                String userID = et_id.getText().toString();
//                String userPassword = et_pass.getText().toString();
//
//                // 빈칸 X, ID, Password 미입력시 로그인 X
//                if(userID.equals("") || userPassword.equals("") ){
//                    Toast.makeText(getApplicationContext(),"ID, Password를 입력해주세요.",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try{
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//
//
//                            if(success){
//                                Toast.makeText(Login.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
//
//                                String userID = jsonResponse.getString("userID");
//                                String userPassword = jsonResponse.getString("userPassword");
//                                Intent intent = new Intent(Login.this, MainActivity2.class);
//                                // 로그인 하면서 사용자 정보 넘기기
//                                intent.putExtra("userID", userID);
//                                intent.putExtra("userPassword", userPassword);
//                                startActivity(intent);
//
//                            } else {
//                                Toast.makeText(Login.this,"로그인 실패", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        } catch(Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(Login.this);
//                queue.add(loginRequest);
//
//            }
//        });
//
//
//        // 회원가입 버튼을 클릭 시 수행
//        btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Login.this, Signup_Php_Mysql.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//
//    }
//}
