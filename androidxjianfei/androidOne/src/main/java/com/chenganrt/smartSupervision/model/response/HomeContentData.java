package com.chenganrt.smartSupervision.model.response;

import java.io.Serializable;
import java.util.List;

public class HomeContentData implements Serializable {
    /**
     * status : 200
     * message : ok
     * data : [{"id":"10340b6d1d70412c88091de988f682cd","title":"关于推荐深圳市燃气行业安全技术管理和事故应急专家的通知","source":"深圳市住房和建设局","publish_time":"2012-02-22","create_time":"2020-07-06 15:12:20","url":"http://zjj.sz.gov.cn/xxgk/tzgg/content/post_6564936.html","is_top":1},{"id":"61825d77bc3b416a9e1f8642fa020da0","title":"深圳市住房和建设局关于参加第十六届国际绿色建筑与建筑节能大会暨新技术与产品博览会的邀请函","source":"深圳市住房和建设局","publish_time":"2020-07-03","create_time":"2020-07-06 15:11:54","url":"http://zjj.sz.gov.cn/xxgk/tzgg/content/post_7848262.html","is_top":0},{"id":"79961735ddd34816baf420aa2de03a8f","title":"深圳市住房公积金管理中心行政执法多人投诉案件专项法律服务机构招标公告","source":"深圳市住房和建设局","publish_time":"2020-07-03","create_time":"2020-07-06 15:11:54","url":"http://zjj.sz.gov.cn/xxgk/tzgg/content/post_7842589.html","is_top":0}]
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
         * id : 10340b6d1d70412c88091de988f682cd
         * title : 关于推荐深圳市燃气行业安全技术管理和事故应急专家的通知
         * source : 深圳市住房和建设局
         * publish_time : 2012-02-22
         * create_time : 2020-07-06 15:12:20
         * url : http://zjj.sz.gov.cn/xxgk/tzgg/content/post_6564936.html
         * is_top : 1
         */

        private String id;
        private String title;
        private String source;
        private String publish_time;
        private String create_time;
        private String url;
        private int is_top;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }
    }
}
