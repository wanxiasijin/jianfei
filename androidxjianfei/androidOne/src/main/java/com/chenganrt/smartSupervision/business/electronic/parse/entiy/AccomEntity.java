package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;


/**
 * Created by Administrator on 2019/12/26.
 */

public class AccomEntity extends Head implements Serializable {
    private static final long serialVersionUID = 4532278023409848016L;

    private String FieldId;
    private String FieldName;

    public AccomEntity() {
    }

    public AccomEntity(Head head) {
        super(head);
    }

    public String getFieldId() {
        return FieldId;
    }

    public void setFieldId(String fieldId) {
        FieldId = fieldId;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }
}
