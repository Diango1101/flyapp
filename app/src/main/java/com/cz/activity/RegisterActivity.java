package com.cz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cz.MyApplication;
import com.cz.R;

public class RegisterActivity extends Activity {
    
    private EditText et_username;
    private EditText et_password;
    private EditText et_password2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        et_username = (EditText) findViewById(R.id.idUsername);
        et_password = (EditText) findViewById(R.id.idPassword);
        et_password2 = (EditText) findViewById(R.id.idPassword2);
    }
    
    public void onRegister(View v) {
        String str_username = et_username.getEditableText().toString();
        String str_password = et_password.getEditableText().toString();
        String str_password2 = et_password2.getEditableText().toString();
        if (str_username.isEmpty() || str_password.isEmpty() || str_password2.isEmpty()) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!str_password.equals(str_password2)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (MyApplication.getInstance().getDbHelper().existUser(str_username, 2)) {
            Toast.makeText(this, "该用户已经存在", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String sql = "insert into tb_user(_username, _password, _type) values('" + str_username + "', '" + str_password + "', 2)";
        MyApplication.getInstance().getDbHelper().update(sql);
        
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        finish();
    }
    
    public void onCancel(View v) {
        finish();
    }
    
}
