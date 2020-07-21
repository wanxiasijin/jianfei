package com.chenganrt.smartSupervision.business.exchange.collect.tab;

import java.io.Serializable;
import java.util.List;

public class CollectData implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;
    private String title;
    private String area;
    private String address;
    private String waste_type_detail;
    private int amount;
    private String views;
    private String collection_volume;
    private String update_time;
    private List<String> files;
    private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
