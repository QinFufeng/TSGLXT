package com.example.wind.liberarymanege.bean;

/**
 * Created by wind on 2018/1/15.
 */

public class TUser {
    private int id;
    private String username;
    private String userpassword;
    private String sex;
    private String phone;
    private String email;
    private String photo;
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public TUser(String username, String userpassword, String sex, String phone, String email, String photo) {
        this.username = username;
        this.userpassword = userpassword;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
    }

    public TUser() {
    }

    @Override
    public String toString() {
        return "TUser{" +
                "username='" + username + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\''  +
                '}';
    }
}
