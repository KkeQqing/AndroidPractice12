package com.example.androidpractice12;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailActivity extends AppCompatActivity {

    private TextView textViewDetailUsername, textViewDetailPassword, textViewDetailPhone;
    private Button btnDetailBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        textViewDetailUsername = findViewById(R.id.textViewDetailUsername);
        textViewDetailPassword = findViewById(R.id.textViewDetailPassword);
        textViewDetailPhone = findViewById(R.id.textViewDetailPhone);
        btnDetailBack = findViewById(R.id.btnDetailBack);

        // 从 Intent 中获取传递过来的 User 对象
        User user = (User) getIntent().getSerializableExtra("user_detail");

        if (user != null) {
            textViewDetailUsername.setText("用户名: " + user.getUsername());
            textViewDetailPassword.setText("密码: " + user.getPassword());
            textViewDetailPhone.setText("电话: " + user.getPhone());
        }

        btnDetailBack.setOnClickListener(v -> finish());
    }
}