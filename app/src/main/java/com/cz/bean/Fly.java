package com.cz.bean;

import java.io.Serializable;
import java.util.Date;

public class Fly implements Serializable {
    private long id;
    private String flyId;
    private String srcCity;
    private String dstCity;
    private int hour;
    private int minute;
    private long flyTime;//飞行时长（分钟）
    private double price;
    
    public Fly(long id, String flyId, String srcCity, String dstCity, int hour, int minute, long flyTime, double price) {
        this.id = id;
        this.flyId = flyId;
        this.srcCity = srcCity;
        this.dstCity = dstCity;
        this.hour = hour;
        this.minute = minute;
        this.flyTime = flyTime;
        this.price = price;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getFlyId() {
        return flyId;
    }
    
    public void setFlyId(String flyId) {
        this.flyId = flyId;
    }
    
    public String getSrcCity() {
        return srcCity;
    }
    
    public void setSrcCity(String srcCity) {
        this.srcCity = srcCity;
    }
    
    public String getDstCity() {
        return dstCity;
    }
    
    public void setDstCity(String dstCity) {
        this.dstCity = dstCity;
    }
    
    public int getHour() {
        return hour;
    }
    
    public void setHour(int hour) {
        this.hour = hour;
    }
    
    public int getMinute() {
        return minute;
    }
    
    public void setMinute(int minute) {
        this.minute = minute;
    }
    
    public long getFlyTime() {
        return flyTime;
    }
    
    public void setFlyTime(long flyTime) {
        this.flyTime = flyTime;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
}
