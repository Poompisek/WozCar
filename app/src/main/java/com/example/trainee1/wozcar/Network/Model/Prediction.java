package com.example.trainee1.wozcar.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trainee1 on 7/8/2017.
 */

public class Prediction {

    @SerializedName("TagId")
    private String tagId;

    @SerializedName("Tag")
    private String tag;

    @SerializedName("Probability")
    private Double probability;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
