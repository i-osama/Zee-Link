package com.zeeshan_s.zee_link.Model;

public class User {
    String user_name, user_email, user_phone_number, user_password, user_profile_img, user_cover_img;

//    public User(String user_name, String user_email, String user_phone_number, String user_password) {
//        this.user_name = user_name;
//        this.user_email = user_email;
//        this.user_phone_number = user_phone_number;
//        this.user_password = user_password;
//    }
    public User(){}

    public User(String user_name, String user_email, String user_phone_number, String user_password, String user_profile_img, String user_cover_img) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone_number = user_phone_number;
        this.user_password = user_password;
        this.user_profile_img = user_profile_img;
        this.user_cover_img = user_cover_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

//    public String getUser_password() {
//        return user_password;
//    }

    public String getUser_profile_img() {
        return user_profile_img;
    }

    public String getUser_cover_img() {
        return user_cover_img;
    }
}
