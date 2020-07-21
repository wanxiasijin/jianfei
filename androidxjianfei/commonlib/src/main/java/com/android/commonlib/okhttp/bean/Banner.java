package com.android.commonlib.okhttp.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banner {

    @Expose
    private Long id;

    @SerializedName("t")
    private long type;

    @SerializedName("cn")
    private String content;

    @SerializedName("img")
    private String imageSourceUrl;

    @SerializedName("desc")
    private String description;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getType() {
        return this.type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageSourceUrl() {
        return this.imageSourceUrl;
    }

    public void setImageSourceUrl(String imageSourceUrl) {
        this.imageSourceUrl = imageSourceUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", imageSourceUrl='" + imageSourceUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
