
package com.example.habebe.projecthomework.dao.homeworkset;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("question_id")
    private String questionId;
    @SerializedName("proposition")
    private String proposition;
    @SerializedName("answer")
    private List<Answer> answer = new ArrayList<Answer>();

    private int score = 0;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getProposition() {
        return proposition;
    }

    public void setProposition(String proposition) {
        this.proposition = proposition;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
