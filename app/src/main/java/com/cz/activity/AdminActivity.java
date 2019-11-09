package com.cz.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cz.R;
import com.cz.fragment.FragmentFlyList;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    
    private Button rb01;
    
    private FragmentFlyList fg1;
    
    private Fragment curFragment = null;
    
    private String loginUsername;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        
        this.getSupportActionBar().hide();
        
        loginUsername = this.getIntent().getStringExtra("loginUsername");
        
        rb01 = (Button) findViewById(R.id.id_rb01);
        
        rb01.setOnClickListener(this);
        
        setBackColor();
        rb01.setBackgroundColor(Color.GREEN);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fg1 == null) {
            Bundle bundle = new Bundle();
            bundle.putString("loginUsername", loginUsername);
            
            fg1 = new FragmentFlyList();
            fg1.setArguments(bundle);         //设置传递的对象
            
            transaction.add(R.id.fragment_container, fg1);
            curFragment = fg1;
        }
        transaction.commit();
    }
    
    public void setBackColor() {
        rb01.setBackgroundColor(Color.GRAY);
    }
    
    public void hideAllFragment(FragmentTransaction transaction) {
        setBackColor();
        if (fg1 != null) {
            transaction.hide(fg1);
        }
    }
    
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.id_rb01: {
                Bundle bundle = new Bundle();
                bundle.putString("loginUsername", loginUsername);
                
                if (fg1 == null) {
                    fg1 = new FragmentFlyList();
                    fg1.setArguments(bundle);         //设置传递的对象
                    transaction.add(R.id.fragment_container, fg1);
                    curFragment = fg1;
                } else {
                    transaction.show(fg1);
                    curFragment = fg1;
                }
                rb01.setBackgroundColor(Color.GREEN);
            }
            break;
        }
        transaction.commit();
    }
    
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showNormalDialog();
    }
    
    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(AdminActivity.this);
        normalDialog.setTitle(getString(R.string.app_name));
        normalDialog.setMessage("确定退出系统？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        finish();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //for (Fragment fragment : getSupportFragmentManager().getFragments()) {
        curFragment.onActivityResult(requestCode, resultCode, data);
        //}
    }
}