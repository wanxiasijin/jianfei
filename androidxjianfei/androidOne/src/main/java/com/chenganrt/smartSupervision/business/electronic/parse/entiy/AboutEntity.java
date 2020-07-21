package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;


/**
 * Created by Administrator on 2019/5/21.
 */

public class AboutEntity extends Head implements Serializable {
    private static final long serialVersionUID = -8281047997164357451L;

    private String Version;
    private String Website;
    private String Tel;
    private String Company;
    private String Address;

    public AboutEntity() {
    }

    public AboutEntity(Head head) {
        super(head);
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
