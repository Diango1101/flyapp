package com.cz.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cz.bean.Fly;
import com.cz.bean.User;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    
    private String tb_user = "tb_user";
    private String tb_fly = "tb_fly";
    private String tb_item = "tb_item";
    
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub

    }
    
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        
        String sql1 = "create table if not exists " + tb_user + " ("
                + "_username VARCHAR(128),"
                + "_password VARCHAR(128),"
                + "_type integer"
                + ");";
        arg0.execSQL(sql1);
        
        String sql2 = "create table if not exists " + tb_fly + " ("
                + "id Integer,"
                + "flyId VARCHAR(64),"
                + "srcCity VARCHAR(64),"
                + "dstCity VARCHAR(64),"
                + "srcHour Integer,"
                + "srcMinute Integer,"
                + "flyTime Integer,"
                + "price double"
                + ");";
        arg0.execSQL(sql2);
        
        String sql3 = "create table if not exists " + tb_item + " ("
                + "id Integer,"
                + "_username VARCHAR(128),"
                + "flyId VARCHAR(64)"
                + ");";
        arg0.execSQL(sql3);
        
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        String sql1 = "drop table if exists " + tb_user;
        arg0.execSQL(sql1);
        
        String sql2 = "drop table if exists " + tb_fly;
        arg0.execSQL(sql2);
        
        String sql3 = "drop table if exists " + tb_item;
        arg0.execSQL(sql3);
        
        onCreate(arg0);
    }
    
    public boolean update(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        
        db.beginTransaction();
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.i("dbhelper", e.getMessage() + "[" + sql + "]");
            return false;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        
        return true;
    }
    
    public boolean login(String username, String password, int type) {
        String sql = "select _username from tb_user where _username = '" + username + "' and _password = '" + password + "' and _type = " + type + "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        while (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        
        cursor.close();
        
        return false;
    }
    
    public User getUser(String username) {
        String sql = "select _username, _password from tb_user where _username = '" + username + "' and _type = 2";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        if (cursor.moveToNext()) {
            User user = new User(
                    cursor.getString(0),
                    cursor.getString(1));
            cursor.close();
            return user;
        }
        
        cursor.close();
        
        return null;
    }
    
    public boolean existUser(String username, int type) {
        String sql = "select _username from tb_user where _username = '" + username + "' and _type = " + type + "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        //List<AllDiary> list = new ArrayList<AllDiary>();
        while (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        
        cursor.close();
        
        return false;
    }
    
    public boolean getCity(List<String> list, String city) {
        String sql = "select distinct " + city + " from " + tb_fly;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        
        cursor.close();
        
        return true;
    }
    
    public void getFly(List<Fly> list) {
        String sql = "select id, flyId, srcCity, dstCity, srcHour, srcMinute, flyTime, price from tb_fly";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        while (cursor.moveToNext()) {
            int index = 0;
            Fly fly = new Fly(
                    cursor.getLong(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getDouble(index++));
            list.add(fly);
        }
        
        cursor.close();
    }
    
    public boolean existFly(String flyId, String srcCity, String dstCity, int hour, int minute) {
        String sql = "select id from tb_fly where srcCity = '" + srcCity + "' and dstCity = '" + dstCity + "' and srcHour = " + hour + ", srcMinute = " + minute + "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        if (cursor.moveToNext()) {
            cursor.close();
            
            return true;
        }
        
        cursor.close();
        
        return false;
    }
    
    public void getFlyById(List<Fly> recycleList, String flyId, long srcTime) {
        String sql = "select id, flyId, srcCity, dstCity, srcHour, srcMinute, flyTime, price from tb_fly where flyId = '" + flyId + "'";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        while (cursor.moveToNext()) {
            int index = 0;
            Fly fly = new Fly(
                    cursor.getLong(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getDouble(index++));
            recycleList.add(fly);
        }
        
        cursor.close();
    }
    
    public void getFlyByCity(List<Fly> recycleList, String srcCity, String dstCity, long srcTime) {
        String sql = "select id, flyId, srcCity, dstCity, srcHour, srcMinute, flyTime, price from tb_fly where srcCity = '" + srcCity + "' and dstCity = '" + dstCity + "'";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        while (cursor.moveToNext()) {
            int index = 0;
            Fly fly = new Fly(
                    cursor.getLong(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getDouble(index++));
            recycleList.add(fly);
        }
        
        cursor.close();
    }
    
    public void getFlyItem(List<Fly> list, String username) {
        String sql = "select b.id, a.flyId, a.srcCity, a.dstCity, a.srcHour, a.srcMinute, a.flyTime, a.price from tb_fly a, tb_item b where a.flyId = b.flyId and b._username = '" + username + "' order by b.id";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        
        while (cursor.moveToNext()) {
            int index = 0;
            Fly fly = new Fly(
                    cursor.getLong(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getString(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getInt(index++),
                    cursor.getDouble(index++));
            list.add(fly);
        }
        
        cursor.close();
    }
    
}
