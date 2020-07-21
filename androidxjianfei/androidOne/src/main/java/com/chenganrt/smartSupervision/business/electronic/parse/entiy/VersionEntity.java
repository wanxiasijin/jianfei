package com.chenganrt.smartSupervision.business.electronic.parse.entiy;


import com.chenganrt.smartSupervision.business.electronic.okhttp.FType;
import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;



/**
 * Created by dell on 2018/7/5.
 */

public class VersionEntity extends Head implements FType, Serializable {

    private static final long serialVersionUID = 5225007002831689314L;

    //private String version;//版本号
    private String url;//下载地址
    /*private String is_force;//是否强制更新
    private String remark;//更新内容
    private String addtime;//时间
    private String is_update;//是否有更新*/

    public VersionEntity(Head head) {
        super(head);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
