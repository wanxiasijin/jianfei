package com.android.commonlib.okhttp.bean;

public class ImageData {
    private String id;
    private String img_id;
    private String url;
    private String create_time;
    private String image_size;
    private String type;

    public ImageData(String id, String img_id, String url, String create_time, String image_size, String type) {
        this.id = id;
        this.img_id = img_id;
        this.url = url;
        this.create_time = create_time;
        this.image_size = image_size;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
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

    public String getImage_size() {
        return image_size;
    }

    public void setImage_size(String image_size) {
        this.image_size = image_size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
