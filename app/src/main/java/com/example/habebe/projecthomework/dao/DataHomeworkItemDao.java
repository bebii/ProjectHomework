
package com.example.habebe.projecthomework.dao;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataHomeworkItemDao {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("chapter")
    private String chapter;

    @SerializedName("noset")
    private String noSet;

    @SerializedName("detail")
    private String detail;

    @SerializedName("amount")
    private String amount;

    @SerializedName("expdate")
    private String expdate;

    public DataHomeworkItemDao() {

    }

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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getNoSet() {
        return noSet;
    }

    public void setNoSet(String noSet) {
        this.noSet = noSet;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpSent() {
        return expdate;
    }

    public void setExpSent(String expdate) {
        this.expdate = expdate;
    }

    @Override
    public String toString() {
        return "DataHomeworkItemDao{" +
                "name='" + name + '\'' +
                ", chapter='" + chapter + '\'' +
                ", noSet='" + noSet + '\'' +
                ", detail='" + detail + '\'' +
                ", expSent='" + expdate + '\'' +
                '}';
    }
}
