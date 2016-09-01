package com.example.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Habebe on 14/4/2559.
 */
public class ResponseDao {
    @SerializedName("success")      private boolean success;
    @SerializedName("message")      private String message;

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
