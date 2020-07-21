package com.android.commonlib.okhttp.bean;

import java.io.Serializable;
import java.util.List;

public class ExchangeData implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;
    private String waste_type;
    private String title;
    private String area;
    private String address;
    private String company;
    private String amount;
    private String term_validity;
    private String contacts;
    private String tel;
    private String details;
    private String exchange_type;
    private String views;
    private String collection_volume;
    private String issuer_id;
    private String update_time;
    private List<String> files;
    private String waste_type_detail;
    private String is_expire;
    private String is_collected;

    private String is_read;
    private String create_time;

    public ExchangeData(String id, String waste_type, String title, String area, String address, String company, String amount, String term_validity, String contacts, String tel, String details, String exchange_type, String views, String collection_volume, String issuer_id, String update_time, List<String> files, String waste_type_detail, String is_expire, String is_collected) {
        this.id = id;
        this.waste_type = waste_type;
        this.title = title;
        this.area = area;
        this.address = address;
        this.company = company;
        this.amount = amount;
        this.term_validity = term_validity;
        this.contacts = contacts;
        this.tel = tel;
        this.details = details;
        this.exchange_type = exchange_type;
        this.views = views;
        this.collection_volume = collection_volume;
        this.issuer_id = issuer_id;
        this.update_time = update_time;
        this.files = files;
        this.waste_type_detail = waste_type_detail;
        this.is_expire = is_expire;
        this.is_collected = is_collected;
    }

    public ExchangeData(ExchangeData exchangeData) {
        this.id = exchangeData.getId();
        this.waste_type = exchangeData.getWaste_type();
        this.title = exchangeData.getTitle();
        this.area = exchangeData.getArea();
        this.address = exchangeData.getAddress();
        this.company = exchangeData.getCompany();
        this.amount = exchangeData.getAmount();
        this.term_validity = exchangeData.getTerm_validity();
        this.contacts = exchangeData.getContacts();
        this.tel = exchangeData.getTel();
        this.details = exchangeData.getDetails();
        this.exchange_type = exchangeData.getExchange_type();
        this.views = exchangeData.getViews();
        this.collection_volume = exchangeData.getCollection_volume();
        this.issuer_id = exchangeData.getIssuer_id();
        this.update_time = exchangeData.getUpdate_time();
        this.files = exchangeData.getFiles();
        this.waste_type_detail = exchangeData.getWaste_type_detail();
        this.is_expire = exchangeData.getIs_expire();
        this.is_collected = exchangeData.getIs_collected();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaste_type() {
        return waste_type;
    }

    public void setWaste_type(String waste_type) {
        this.waste_type = waste_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTerm_validity() {
        return term_validity;
    }

    public void setTerm_validity(String term_validity) {
        this.term_validity = term_validity;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getExchange_type() {
        return exchange_type;
    }

    public void setExchange_type(String exchange_type) {
        this.exchange_type = exchange_type;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getCollection_volume() {
        return collection_volume;
    }

    public void setCollection_volume(String collection_volume) {
        this.collection_volume = collection_volume;
    }

    public String getIssuer_id() {
        return issuer_id;
    }

    public void setIssuer_id(String issuer_id) {
        this.issuer_id = issuer_id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getWaste_type_detail() {
        return waste_type_detail;
    }

    public void setWaste_type_detail(String waste_type_detail) {
        this.waste_type_detail = waste_type_detail;
    }

    public String getIs_expire() {
        return is_expire;
    }

    public void setIs_expire(String is_expire) {
        this.is_expire = is_expire;
    }

    public String getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(String is_collected) {
        this.is_collected = is_collected;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
