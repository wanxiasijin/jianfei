package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;


/**
 * Created by Administrator on 2019/11/26.
 */

public class ImageEntity extends Head implements Serializable {
    private static final long serialVersionUID = -934048955298243283L;

    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
