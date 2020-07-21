package com.chenganrt.smartSupervision.business.exchange.entity;

import com.android.commonlib.okhttp.bean.ExchangeData;

import java.util.List;

public class ExchangeListResponse {
    private List<ExchangeData> records;
    private String total;
    private String size;
    private String current;
    private String pages;

    public ExchangeListResponse(List<ExchangeData> records, String total, String size, String current, String pages) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = pages;
    }

    public List<ExchangeData> getRecords() {
        return records;
    }

    public void setRecords(List<ExchangeData> records) {
        this.records = records;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
