package com.android.commonlib.okhttp.bean;

public class User {

    private String user_id;
    private int user_type;
    private String user_name;
    private String mobile_phone;
    private String app_account;
    private String url;
    private String project_name;
    private String enter_name;

    public User() {

    }

    public User(String user_id, int user_type, String user_name, String mobile_phone, String app_account, String url, String project_name, String enter_name) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.user_name = user_name;
        this.mobile_phone = mobile_phone;
        this.app_account = app_account;
        this.url = url;
        this.project_name = project_name;
        this.enter_name = enter_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getApp_account() {
        return app_account;
    }

    public void setApp_account(String app_account) {
        this.app_account = app_account;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getEnter_name() {
        return enter_name;
    }

    public void setEnter_name(String enter_name) {
        this.enter_name = enter_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_type=" + user_type +
                ", user_name='" + user_name + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", app_account='" + app_account + '\'' +
                ", url='" + url + '\'' +
                ", project_name='" + project_name + '\'' +
                ", enter_name='" + enter_name + '\'' +
                '}';
    }
}
