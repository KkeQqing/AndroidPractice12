package com.example.androidpractice12;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextPhone;
    private Button btnSave, btnBack;
    private MySQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        dbHelper = new MySQLiteHelper(this);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前活动，返回上一个
            }
        });
    }

    private void saveUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "所有字段都不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(username, password, phone);
        boolean isAdded = dbHelper.addUser(newUser);

        if (isAdded) {
            Toast.makeText(this, "用户添加成功", Toast.LENGTH_SHORT).show();
            // 清空输入框
            editTextUsername.setText("");
            editTextPassword.setText("");
            editTextPhone.setText("");
        } else {
            Toast.makeText(this, "用户添加失败（用户名可能已存在）", Toast.LENGTH_SHORT).show();
        }
    }
}