package com.elegion.myfirstapplication;

import java.io.Serializable;

public class User implements Serializable {
    private String mLogin;
    private String mPassword;

    public User(String login, String password) {
        mLogin = login;
        mPassword = password;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
