package com.bitzh.hospitalsystem.model;

public class Doctor {
    private int id;
    private String name;
    private String specialty;
    private String availableTime;
    private String username;
    private String password;

    // 无参数构造函数
    public Doctor() {
    }

    // 有参数构造方法
    public Doctor(int id, String name, String specialty, String availableTime, String username, String password) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.availableTime = availableTime;
        this.username = username;
        this.password = password;
    }

    // getter 和 setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    // 额外的 getter 和 setter 方法
    public String getAvailable_time() {
        return availableTime;
    }

    public void setAvailable_time(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID(){return String.valueOf(id);}
}
