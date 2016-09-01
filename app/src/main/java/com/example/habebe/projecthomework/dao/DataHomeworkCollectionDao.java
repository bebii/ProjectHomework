
package com.example.habebe.projecthomework.dao;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataHomeworkCollectionDao {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<DataHomeworkItemDao> data = new ArrayList<DataHomeworkItemDao>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataHomeworkItemDao> getData() {
        return data;
    }

    public void setData(List<DataHomeworkItemDao> data) {
        this.data = data;
    }

}

