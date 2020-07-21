package com.chenganrt.smartSupervision.business.exchange.filter.entity;

import java.util.List;

public class FilterEntity {
    /**
     * 发布时间
     */
    private List<FilterSelectedEntity> time;
    /**
     * 类别
     */
    private List<FilterSelectedEntity> type;
    /**
     * 区域
     */
    private List<FilterSelectedEntity> area;
    /**
     * 筛选
     */
    private List<FilterMulSelectEntity> mulSelect;

    public List<FilterSelectedEntity> getTime() {
        return time;
    }

    public void setTime(List<FilterSelectedEntity> time) {
        this.time = time;
    }

    public List<FilterSelectedEntity> getType() {
        return type;
    }

    public void setType(List<FilterSelectedEntity> type) {
        this.type = type;
    }

    public List<FilterSelectedEntity> getArea() {
        return area;
    }

    public void setArea(List<FilterSelectedEntity> area) {
        this.area = area;
    }

    public List<FilterMulSelectEntity> getMulSelect() {
        return mulSelect;
    }

    public void setMulSelect(List<FilterMulSelectEntity> mulSelect) {
        this.mulSelect = mulSelect;
    }
}
