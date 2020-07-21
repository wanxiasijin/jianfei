package com.android.commonlib.okhttp.bean;

public class Account {
    private String business_type;
    private String telephone;
    private String code;
    private String account;
    private String password;
    private String wx_id;

    public Account() {

    }

    public Account(String business_type, String telephone) {
        this.telephone = telephone;
        this.business_type = business_type;
    }

    public Account(String business_type, String telephone, String code) {
        this.telephone = telephone;
        this.business_type = business_type;
        this.code = code;
    }

    public Account(String business_type, String telephone, String account, String password) {
        this.telephone = telephone;
        this.business_type = business_type;
        this.account = account;
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "MessageCode{" +
                "telephone='" + telephone + '\'' +
                ", business_type='" + business_type + '\'' +
                ", code='" + code + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
