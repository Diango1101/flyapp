package com.cz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cz.MyApplication;
import com.cz.R;

public class LoginActivity extends Activity {
    
    private EditText etUsername;
    private EditText etPassword;
    
    private int type = 2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        etUsername = (EditText) findViewById(R.id.idUsername);
        etPassword = (EditText) findViewById(R.id.idPassword);
        
        ((RadioButton) findViewById(R.id.rb01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
            }
        });
        ((RadioButton) findViewById(R.id.rb02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
            }
        });
    }
    
    public void onLogin(View v) {
        String str_username = etUsername.getEditableText().toString();
        String str_password = etPassword.getEditableText().toString();
        if (str_username.isEmpty() || str_password.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (MyApplication.getInstance().getDbHelper().login(str_username, str_password, type)) {
            etUsername.setText("");
            etPassword.setText("");
            if (type == 1) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                intent.putExtra("loginUsername", str_username);
                this.startActivity(intent);
            } else if (type == 2) {
                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                intent.putExtra("loginUsername", str_username);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void onCancel(View v) {
        this.finish();
    }
    
    public void onRegister(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        this.startActivity(intent);
    }
    
    
}
