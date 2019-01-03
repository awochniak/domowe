package com.example.arkadiuszwochniak.domowe.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Photos {

    @SerializedName("title")
    public String title;
    @SerializedName("thumbnailUrl")
    public String thumbnailUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        }
    }