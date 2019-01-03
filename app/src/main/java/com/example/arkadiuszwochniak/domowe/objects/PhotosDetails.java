package com.example.arkadiuszwochniak.domowe.objects;

import com.google.gson.annotations.SerializedName;

public class PhotosDetails {

    @SerializedName("title")
    public String titleDetail;
    @SerializedName("url")
    public String url;

    public String getTitleDetail() {
        return titleDetail;
    }

    public String getUrl() {
        return url;
    }
}
