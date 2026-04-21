package com.example.androidpractice12;

import java.io.Serializable;

// 实现 Serializable 接口，以便可以通过 Intent 传递 User 对象
public class User implements Serializable {
    private String username;
    private String password;
    private String phone;

    // 构造函数
    public User(String username, String password, String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    // Getter 和 Setter 方法
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}