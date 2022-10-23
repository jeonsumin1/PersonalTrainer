package com.example.Personal_Trainer;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Signup_Php_Mysql extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    private static String IP_ADDRESS = "3.38.94.242"; //본인 IP주소를 넣으세요.
    private static String TAG = "phptest"; //phptest log 찍으려는 용도

    private TextView signup_id;
    private TextView signup_pwd;
    private TextView signup_pwd2;
    private TextView signup_name;
    private TextView signup_email;
    private TextView signup_date;
    private Button signup_button,validateButton;
    private TextView signup_height;
    private TextView signup_gender;
    private ImageView back;
    private AlertDialog dialog;
    private boolean validate=false;
    private String userGender;


    private Spinner spinner;
    private ImageView imageView2;


    private TextView mTextViewResult;

    // 뒤로가기 버튼
    public void Back(View view){
        super.onBackPressed();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up);

        signup_id = (EditText) findViewById(R.id.signup_id);
        signup_pwd = (EditText) findViewById(R.id.signup_pwd);
        signup_pwd2 = (EditText) findViewById(R.id.signup_pwd2);
        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_email = (EditText) findViewById(R.id.signup_email);

//        signup_gender = (EditText) findViewById(R.id.signup_gender);
        signup_date = (EditText) findViewById(R.id.signup_date);
        signup_height = (EditText) findViewById(R.id.signup_height);
        signup_button = (Button) findViewById(R.id.signup_button);
//        back = (ImageView) findViewById(R.id.back);
        final Button validateButton = (Button) findViewById(R.id.validateButton);



        RadioGroup genderGroup = (RadioGroup)findViewById(R.id.genderGroup);

        //라디오버튼이 눌리면 값을 바꿔주는 부분
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton genderButton = (RadioButton)findViewById(i);
                int genderGroupID = genderGroup.getCheckedRadioButtonId();
                userGender = ((RadioButton)findViewById(genderGroupID)).getText().toString();//초기화 값을 지정해줌
                userGender = genderButton.getText().toString();
            }
        });


        validateButton.setOnClickListener(new View.OnClickListener() {//id중복체크
            @Override
            public void onClick(View view) {
                String userID=signup_id.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( Signup_Php_Mysql.this );
                    dialog=builder.setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder( Signup_Php_Mysql.this );
                                dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                signup_id.setEnabled(false);
                                validate=true;
                                validateButton.setText("확인");
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( Signup_Php_Mysql.this );
                                dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest=new ValidateRequest(userID, responseListener);
                RequestQueue queue= Volley.newRequestQueue(Signup_Php_Mysql.this);
                queue.add(validateRequest);

            }
        });


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = signup_id.getText().toString().trim();
                String userPassword = signup_pwd.getText().toString().trim();
                String confirm_password = signup_pwd2.getText().toString().trim();
                String userName = signup_name.getText().toString().trim();
                String userEmail = signup_email.getText().toString().trim();
                int genderGroupID = genderGroup.getCheckedRadioButtonId();
                userGender = ((RadioButton)findViewById(genderGroupID)).getText().toString();//초기화 값을 지정해줌
//                String userGender = signup_gender.getText().toString().trim();
                String userBirth = signup_date.getText().toString().trim();
                String userHeight = signup_height.getText().toString().trim() ;

//                String userGender = spinner.getSelectedItem().toString().trim();


                //회원가입을 할 때 예외 처리를 해준 것이다.
                if (userID.equals("")  || userPassword.equals("") || confirm_password.equals("") || userName.equals("")|| userEmail.equals("") || userGender.equals("- 성별을 선택해 주세요 -") || userBirth.equals("") || userHeight.equals("") )
                {
                    Toast.makeText(Signup_Php_Mysql.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {

                    if(userPassword.equals(confirm_password)) {
                        if(userPassword.length()<=5){
                            Toast.makeText(Signup_Php_Mysql.this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!userEmail.contains("@") || !userEmail.contains(".com")){

                            Toast.makeText(Signup_Php_Mysql.this, "아이디에 @ 및 .com을 포함시키세요.", Toast.LENGTH_SHORT).show();
                        }

                        else if(userBirth.length()<=7 || Integer.parseInt(String.valueOf(userBirth.charAt(0))) >= 3 || Integer.parseInt(String.valueOf(userBirth.charAt(4)))>1
                                || Integer.parseInt(String.valueOf(userBirth.charAt(5)))==0  || Integer.parseInt(String.valueOf(userBirth.charAt(6)))>3
                                ||(Integer.parseInt(String.valueOf(userBirth.charAt(6)))==3  && Integer.parseInt(String.valueOf(userBirth.charAt(7)))>1
                                || (Integer.parseInt(String.valueOf(userBirth.charAt(4)))==1 &&  Integer.parseInt(String.valueOf(userBirth.charAt(5)))>2)
                                ||Integer.parseInt(String.valueOf(userBirth.charAt(7)))==0)
                        ) {
                            Toast.makeText(Signup_Php_Mysql.this, "생년월일 8자 이상 및 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();

                        }
                        else if (userBirth.length()<=7 || ( Integer.parseInt(String.valueOf(userBirth.charAt(0)))==2  && Integer.parseInt(String.valueOf(userBirth.charAt(1)))>0
                                && Integer.parseInt(String.valueOf(userBirth.charAt(2)))>2  && Integer.parseInt(String.valueOf(userBirth.charAt(3)))>2)|| Integer.parseInt(String.valueOf(userBirth.charAt(6)))>3
                                || (Integer.parseInt(String.valueOf(userBirth.charAt(6)))==3  && Integer.parseInt(String.valueOf(userBirth.charAt(7)))>1)
                                ||(Integer.parseInt(String.valueOf(userBirth.charAt(4)))==1 &&  Integer.parseInt(String.valueOf(userBirth.charAt(5)))>2
                                ||Integer.parseInt(String.valueOf(userBirth.charAt(7)))==0)
                        ) {
                            Toast.makeText(Signup_Php_Mysql.this, "생년월일 8자 이상 및 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            InsertData task = new InsertData(); //PHP 통신을 위한 InsertData 클래스의 task 객체 생성
                            //본인이 접속할 PHP 주소와 보낼 데이터를 입력해준다.
                            task.execute("http://3.38.94.242/register.php",userID,userPassword,confirm_password,userName,userEmail,userGender,userBirth,userHeight);
                            Toast.makeText(Signup_Php_Mysql.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Signup_Php_Mysql.this, RegisterCompleteActivity.class);
                            startActivity(intent);
                            finish();

                        }


                    }
                    else {
                        Toast.makeText(Signup_Php_Mysql.this, "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }


                }





            }
        });


    }

    class InsertData extends AsyncTask<String,Void,String> { // 통신을 위한 InsertData 생성
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행 다이얼로그 생성
            progressDialog = ProgressDialog.show(Signup_Php_Mysql.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss(); //onPostExcute 에 오게되면 진행 다이얼로그 취소
//            mTextViewResult.setText(result); //xml의 작은 공간인 mTextViewResult에 result값 넣기
            Log.d(TAG, "POST response  - " + result); // result 값 확인하기
        }


        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... params) {
            /*
            PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터를 준비한다.
            POST 방식으로 데이터 전달시에는 데이터가 주소에 직접 입력되지 않는다.
            이 값들은 InsertData 객체.excute 에서 매개변수로 준 값들이 배열 개념으로 차례대로 들어가
            값을 받아오는 개념이다.
             */
            String serverURL = (String) params[0];

            String userID = (String)params[1];
            String userPassword = (String)params[2];
            String confirm_password = (String)params[3];
            String userName = (String)params[4];
            String userEmail = (String)params[5];
            String userGender = (String)params[6];
            String userBirth = (String)params[7];
            String userHeight = (String)params[8];
//            String useranswer = (String)params[8];


            /*
            HTTP 메세지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야한다.
            전송할 데이터는 "이름=값" 형식이며 여러 개를 보내야 할 경우에 항목 사이에 &를 추가해준다.
            여기에 적어준 이름들은 나중에 PHP에서 사용하여 값을 얻게 된다.
             */
            String postParameters ="&userID="+userID+"&userPassword="+ userPassword
                    +"&confirm_password="+confirm_password+"&userName="+userName+"&userEmail="+userEmail+"&userGender="+userGender
                    +"&userBirth="+userBirth+"&userHeight="+userHeight; //+"&userPassword2="+userPassword2

            try{ // HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송한다.
                URL url = new URL(serverURL); //주소가 저장된 변수를 이곳에 입력한다.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000); // 5초안에 응답 오지 않으면 예외가 발생

                httpURLConnection.setConnectTimeout(5000); // 5초안에 연결이 안되면 예외가 발생

                httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST

                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();

                //전송할 데이터가 저장된 변수를 이곳에 입력. 인코딩을 고려해줘야 하기 때문에 UTF-8 형식으로 넣어준다.
                outputStream.write(postParameters.getBytes("UTF-8"));

                Log.d("php postParameters_데이터 : ",postParameters); //postParameters의 값이 정상적으로 넘어왔나 Log를 찍어줬다.

                outputStream.flush();//현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비움
                outputStream.close(); //객체를 닫음으로써 자원을 반납한다.


                int responseStatusCode = httpURLConnection.getResponseCode(); //응답을 읽음.
                Log.d(TAG, "POST response code-" + responseStatusCode);

                InputStream inputStream;

                if(responseStatusCode == httpURLConnection.HTTP_OK){ //만약 정상적인 응답 데이터 라면
                    inputStream=httpURLConnection.getInputStream();
                    Log.d("php정상: ","정상적으로 출력"); //로그 메세지로 정상적으로 출력을 찍는다.
                }
                else {
                    inputStream = httpURLConnection.getErrorStream(); //만약 에러가 발생한다면
                    Log.d("php비정상: ","비정상적으로 출력"); // 로그 메세지로 비정상적으로 출력을 찍는다.
                }

                // StringBuilder를 사용하여 수신되는 데이터를 저장한다.
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) !=null ) {
                    sb.append(line);
                }

                bufferedReader.close();

                Log.d("php 값 :", sb.toString());


                //저장된 데이터를 스트링으로 변환하여 리턴값으로 받는다.
                return  sb.toString();


            }

            catch (Exception e) {

                Log.d(TAG, "InsertData: Error",e);

                return  new String("Error " + e.getMessage());

            }

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
