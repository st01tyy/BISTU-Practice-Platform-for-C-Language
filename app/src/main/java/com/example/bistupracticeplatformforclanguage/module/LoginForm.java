package com.example.bistupracticeplatformforclanguage.module;

public class LoginForm
{
    private String id;
    private String pw;
    private boolean autoLogin;

    public LoginForm(String id, String pw) {
        this.id = id;
        this.pw = pw;
        this.autoLogin = false;
    }

    public LoginForm(String id, String pw, boolean autoLogin) {
        this.id = id;
        this.pw = pw;
        this.autoLogin = autoLogin;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }
}
