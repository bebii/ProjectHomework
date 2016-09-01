package com.example.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Habebe on 14/4/2559.
 */
public class CourseCollectionDao {
    @SerializedName("success")      private boolean success;
    @SerializedName("message")      private String message;
    @SerializedName("data")         private List<CourseDao> data = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<CourseDao> getData() {
        return data;
    }

    public void setData(List<CourseDao> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
