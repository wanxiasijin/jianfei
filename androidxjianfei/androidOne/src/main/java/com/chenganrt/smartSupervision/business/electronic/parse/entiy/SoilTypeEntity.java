package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.FType;
import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;
import java.util.List;



/**
 * Created by Administrator on 2019/11/27.
 */

public class SoilTypeEntity extends Head implements Serializable,FType {
    private static final long serialVersionUID = -8079334196261654025L;

    private String value;
    private String text;

    private List<SoilTypeEntity> soilTypeEntities;

    public SoilTypeEntity() {
    }

    public SoilTypeEntity(Head head) {
        super(head);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SoilTypeEntity> getSoilTypeEntities() {
        return soilTypeEntities;
    }

    public void setSoilTypeEntities(List<SoilTypeEntity> soilTypeEntities) {
        this.soilTypeEntities = soilTypeEntities;
    }
}
