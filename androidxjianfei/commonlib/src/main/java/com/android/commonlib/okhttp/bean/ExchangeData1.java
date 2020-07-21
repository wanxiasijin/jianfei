package com.android.commonlib.okhttp.bean;

import java.util.List;

public class ExchangeData1 {
    private String id;
    private String title;
    private String area;
    private String address;
    private String waste_type_detail;
    private String amount;
    private String create_time;
    private List<ImageData> files;

    public ExchangeData1(String id, String title, String area, String address, String waste_type_detail, String amount, String create_time, List<ImageData> files) {
        this.id = id;
        this.title = title;
        this.area = area;
        this.address = address;
        this.waste_type_detail = waste_type_detail;
        this.amount = amount;
        this.create_time = create_time;
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<ImageData> getFiles() {
        return files;
    }

    public void setFiles(List<ImageData> files) {
        this.files = files;
    }
}
