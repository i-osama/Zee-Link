package com.zeeshan_s.zee_link.Model;

public class User {
    String user_name, user_email, user_phone_number, user_password, profileURL, coverURL;

//    public User(String user_name, String user_email, String user_phone_number, String user_password) {
//        this.user_name = user_name;
//        this.user_email = user_email;
//        this.user_phone_number = user_phone_number;
//        this.user_password = user_password;
//    }
    public User(){}

    public User(String user_name, String user_email, String user_phone_number, String user_password, String profileURL, String coverURL) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone_number = user_phone_number;
        this.user_password = user_password;
        this.profileURL = profileURL;
        this.coverURL = coverURL;
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

    public String getProfileURL() {
        return profileURL;
    }

    public String getCoverURL() {
        return coverURL;
    }
}
