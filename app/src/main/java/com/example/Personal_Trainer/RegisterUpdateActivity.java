package com.example.Personal_Trainer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class RegisterUpdateActivity extends AppCompatActivity {

//    private TextView signup_id, signup_name, signup_email, et_name, et_email, et_birth, et_height;
    private EditText et_id, et_name, et_email, et_birth, et_height;
    private Button Success;
    private RadioGroup et_gender;

    public void Back(View view){
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerupdate);

        // 회원아이디
        et_id= (EditText) findViewById(R.id.et_id);
        Intent intent = getIntent();
        String userID=intent.getStringExtra("userID");
        et_id.setText(userID);

        // 회원 이름
        et_name= (EditText) findViewById(R.id.et_name);
        String userName=intent.getStringExtra("userName");
        et_name.setText(userName);

        // 이메일
        et_email= (EditText) findViewById(R.id.et_email);
        String userEmail=intent.getStringExtra("userEmail");
        et_email.setText(userEmail);

        // Birth
        et_birth= (EditText) findViewById(R.id.et_birth);
        String userBirth=intent.getStringExtra("userBirth");
        et_birth.setText(userBirth);

        // Height
        TextView et_height= (EditText) findViewById(R.id.et_height);
        String userHeight=intent.getStringExtra("userHeight");
        et_height.setText(userHeight);



        Success = findViewById(R.id.btn_register);
        Success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterUpdateActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }

}
