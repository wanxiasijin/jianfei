package com.chenganrt.smartSupervision.business.home;

import com.chenganrt.smartSupervision.model.base.BaseModel;



/**
 * Created by Administrator on 2019/12/25.
 */

public class ContentEntity extends BaseModel{
    public String title;


    public String source;

    public String time;

    public int is_top;

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ContentEntity(int isTop, String title, String source, String time) {
        this.is_top = isTop;
        this.title = title;
        this.source = source;
        this.time = time;
    }
}
