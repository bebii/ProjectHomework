
package com.example.habebe.projecthomework.dao.homeworkset;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeworkSetCollection {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("homeworkset")
    private List<Homeworkset> homeWorkSet = new ArrayList<Homeworkset>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Homeworkset> getHomeWorkSet() {
        return homeWorkSet;
    }

    public void setHomeWorkSet(List<Homeworkset> homeWorkSet) {
        this.homeWorkSet = homeWorkSet;
    }
}
