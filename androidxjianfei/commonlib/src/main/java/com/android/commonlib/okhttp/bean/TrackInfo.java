package com.android.commonlib.okhttp.bean;

import java.util.List;

public class TrackInfo {
    private int type = -1;
    private String area_name;
    private List<TrackData> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public List<TrackData> getList() {
        return list;
    }

    public void setList(List<TrackData> list) {
        this.list = list;
    }
}
