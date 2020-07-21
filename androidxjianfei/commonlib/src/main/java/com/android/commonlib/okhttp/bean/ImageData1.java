package com.android.commonlib.okhttp.bean;

public class ImageData1 {

    private String file_name;
    private String image_size;
    private String url;

    public ImageData1(String file_name, String image_size, String url) {
        this.file_name = file_name;
        this.image_size = image_size;
        this.url = url;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getImage_size() {
        return image_size;
    }

    public void setImage_size(String image_size) {
        this.image_size = image_size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
