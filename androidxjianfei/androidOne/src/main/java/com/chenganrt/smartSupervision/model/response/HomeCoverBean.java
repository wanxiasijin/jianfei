package com.chenganrt.smartSupervision.model.response;

import java.io.Serializable;
import java.util.List;

public class HomeCoverBean implements Serializable {
    /**
     * status : 200
     * message : 查询轮播图成功
     * data : [{"id":"1","img_url":"https://dev.szsti.org/base/api/image/upload/download/6f21808889b34c94a17ac09fb6d37599.jpg","link_url":"/home","create_time":"2020-05-18 00:00:00","sort_num":0},{"id":"2","img_url":"https://dev.szsti.org/base/api/image/upload/download/71cecc6b210542feb15b855246fd6348.jpg","link_url":"/home","create_time":"2020-05-18 15:35:14","sort_num":1},{"id":"3","img_url":"https://dev.szsti.org/base/api/image/upload/download/264b3333f8324ee3ae761eae32950ece.jpg","link_url":"/home","create_time":"2020-05-18 15:35:14","sort_num":2}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * img_url : https://dev.szsti.org/base/api/image/upload/download/6f21808889b34c94a17ac09fb6d37599.jpg
         * link_url : /home
         * create_time : 2020-05-18 00:00:00
         * sort_num : 0
         */

        private String id;
        private String img_url;
        private String link_url;
        private String create_time;
        private int sort_num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getSort_num() {
            return sort_num;
        }

        public void setSort_num(int sort_num) {
            this.sort_num = sort_num;
        }
    }
}
