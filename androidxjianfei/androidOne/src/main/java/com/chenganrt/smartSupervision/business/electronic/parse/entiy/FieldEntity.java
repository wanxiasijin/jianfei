package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;



/**
 * Created by Administrator on 2019/7/9.
 */

public class FieldEntity extends Head implements Serializable {
    private static final long serialVersionUID = 6386006795569885611L;

    public FieldEntity() {
    }

    public FieldEntity(Head head) {
        super(head);
    }

    private String FieldTypeID;
    private String FieldTypeName;

    public String getFieldTypeID() {
        return FieldTypeID;
    }

    public void setFieldTypeID(String fieldTypeID) {
        FieldTypeID = fieldTypeID;
    }

    public String getFieldTypeName() {
        return FieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        FieldTypeName = fieldTypeName;
    }
}
