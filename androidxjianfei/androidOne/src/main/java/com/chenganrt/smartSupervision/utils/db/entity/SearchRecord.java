package com.chenganrt.smartSupervision.utils.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SearchRecord {

    @Id(autoincrement = true)
    private Long id;
    private String keyword;
    private boolean isChecked;

    public SearchRecord(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    @Generated(hash = 1646728775)
    public SearchRecord(Long id, String keyword, boolean isChecked) {
        this.id = id;
        this.keyword = keyword;
        this.isChecked = isChecked;
    }

    @Generated(hash = 839789598)
    public SearchRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
