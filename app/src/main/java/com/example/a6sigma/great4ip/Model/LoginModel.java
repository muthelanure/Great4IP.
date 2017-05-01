package com.example.a6sigma.great4ip.Model;

/**
 * Created by lenovo on 21/02/2017.
 */

public class LoginModel {
    private String mEmail;
    private String mPassword;

    public LoginModel(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
