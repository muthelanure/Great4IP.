package com.example.a6sigma.great4ip.Model;

/**
 * Created by lenovo on 07/03/2017.
 */

public class SignUpModel {
    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;

    public SignUpModel(String email, String password, String confirmPassword) {
        mEmail = email;
        mPassword = password;
        mConfirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return mConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        mConfirmPassword = confirmPassword;
    }
}
