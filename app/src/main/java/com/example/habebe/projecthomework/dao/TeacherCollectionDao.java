package com.example.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Habebe on 14/4/2559.
 */
public class TeacherCollectionDao {
    @SerializedName("success")      private boolean success;
    @SerializedName("data")         private List<TeacherDao> data = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TeacherDao> getData() {
        return data;
    }

    public void setData(List<TeacherDao> data) {
        this.data = data;
    }
}
