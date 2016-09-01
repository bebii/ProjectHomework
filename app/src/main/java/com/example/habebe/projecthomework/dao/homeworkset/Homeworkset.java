
package com.example.habebe.projecthomework.dao.homeworkset;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Homeworkset {

    @SerializedName("homeworkset_id")
    private String homeworksetId;
    @SerializedName("course")
    private String course;
    @SerializedName("chapter")
    private String chapter;
    @SerializedName("no_set")
    private String noSet;
    @SerializedName("level")
    private String level;
    @SerializedName("datahomework_id")
    private String dataHomeworkId;
    @SerializedName("amount")
    private String amount;
    @SerializedName("question")
    private List<Question> question = new ArrayList<Question>();

    public String getHomeworksetId() {
        return homeworksetId;
    }

    public void setHomeworksetId(String homeworksetId) {
        this.homeworksetId = homeworksetId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDataHomeworkId() {
        return dataHomeworkId;
    }

    public void setDataHomeworkId(String dataHomeworkId) {
        this.dataHomeworkId = dataHomeworkId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }
}
