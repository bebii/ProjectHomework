package com.example.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Habebe on 14/4/2559.
 */
public class CourseDao {
    @SerializedName("id")   private String id;
    @SerializedName("name") private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
