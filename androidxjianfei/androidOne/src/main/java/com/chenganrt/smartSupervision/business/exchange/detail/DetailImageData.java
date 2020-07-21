package com.chenganrt.smartSupervision.business.exchange.detail;

public class DetailImageData {

    private String id;
    private String image_Id;
    private String url;
    private String create_time;
    protected long exchange_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_Id() {
        return image_Id;
    }

    public void setImage_Id(String image_Id) {
        this.image_Id = image_Id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public long getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(long exchange_id) {
        this.exchange_id = exchange_id;
    }
}
