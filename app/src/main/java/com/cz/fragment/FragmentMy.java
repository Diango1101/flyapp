package com.cz.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cz.MyApplication;
import com.cz.R;
import com.cz.activity.LoginActivity;
import com.cz.bean.User;

public class FragmentMy extends Fragment {
    private String loginUsername;
    private Activity activity;
    
    private EditText etUsername, etPassword, etPwd2;
    
    public FragmentMy() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        activity = this.getActivity();
        
        loginUsername = this.getArguments().getString("loginUsername");
        
        etUsername = (EditText) view.findViewById(R.id.idUsername);
        etPassword = (EditText) view.findViewById(R.id.idPassword);
        etPwd2 = (EditText) view.findViewById(R.id.idPwd2);
        
        User user = MyApplication.getInstance().getDbHelper().getUser(loginUsername);
        etUsername.setText(user.getUsername());
        etPassword.setText(user.getPassword());
        etPwd2.setText("");
        
        ((Button) view.findViewById(R.id.idModify)).setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                String sql = "update tb_user set _password = '" + etPwd2.getEditableText().toString() + "' where _username = '" + loginUsername + "'";
                MyApplication.getInstance().getDbHelper().update(sql);
                
                Toast.makeText(activity, "密码修改成功", Toast.LENGTH_SHORT).show();
            }
        });
        
        ((Button) view.findViewById(R.id.idQuit)).setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
}
