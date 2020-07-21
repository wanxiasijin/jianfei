package com.chenganrt.smartSupervision.business.exchange.publish.tab;

public class PublishData {

    private String exchange_type;
    private String title;
    private String area;
    private String address;
    private String waste_type_detail;
    private String company;
    private String term_validity;
    private String contacts;
    private String tel;
    private String details;
    private String issuer_id;
    private String urls;

    public PublishData(String exchange_type, String title, String area, String address, String waste_type_detail, String company, String term_validity, String contacts, String tel, String details, String issuer_id, String urls) {
        this.exchange_type = exchange_type;
        this.title = title;
        this.area = area;
        this.address = address;
        this.waste_type_detail = waste_type_detail;
        this.company = company;
        this.term_validity = term_validity;
        this.contacts = contacts;
        this.tel = tel;
        this.details = details;
        this.issuer_id = issuer_id;
        this.urls = urls;
    }

    public String getExchange_type() {
        return exchange_type;
    }

    public void setExchange_type(String exchange_type) {
        this.exchange_type = exchange_type;
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

    public String getWaste_type_detail() {
        return waste_type_detail;
    }

    public void setWaste_type_detail(String waste_type_detail) {
        this.waste_type_detail = waste_type_detail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getIssuer_id() {
        return issuer_id;
    }

    public void setIssuer_id(String issuer_id) {
        this.issuer_id = issuer_id;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
