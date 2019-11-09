package com.cz;

import android.app.Application;

import com.cz.util.DBHelper;

public class MyApplication extends Application {
    
    private DBHelper dbHelper;
    
    public DBHelper getDbHelper() {
        return dbHelper;
    }
    
    private static MyApplication instance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;//存储引用
        dbHelper = new DBHelper(this, "com_database", null, 2);
        
        if (!dbHelper.existUser("admin", 1)) {
            dbHelper.update("insert into tb_user(_username, _password, _type) values('admin', 'admin', 1)");//给用户一个默认的用户名和密码，admin
        }
    }
    
    public static MyApplication getInstance() {
        return instance;
    }
    
}
