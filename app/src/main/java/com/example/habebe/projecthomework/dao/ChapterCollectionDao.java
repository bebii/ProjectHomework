package com.example.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Habebe on 23/5/2559.
 */
public class ChapterCollectionDao {
    @SerializedName("success")      private boolean success;
    @SerializedName("message")      private String message;
    @SerializedName("data")         private List<ChapterDao> data = new ArrayList<>();

    public List<ChapterDao> getData() {
        return data;
    }

    public void setData(List<ChapterDao> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
