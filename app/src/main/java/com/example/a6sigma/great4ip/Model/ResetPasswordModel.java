package com.example.a6sigma.great4ip.Model;

/**
 * Created by lenovo on 09/03/2017.
 */

public class ResetPasswordModel {
    private String mEmail;

    public ResetPasswordModel(String email) {
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
