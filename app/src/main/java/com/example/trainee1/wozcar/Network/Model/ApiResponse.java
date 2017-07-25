package com.example.trainee1.wozcar.Network.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Trainee1 on 7/7/2017.
 */

public class ApiResponse {
    @SerializedName("Id")
    private String id;

    @SerializedName("Project")
    private String project;

    @SerializedName("Iteration")
    private String iteration;

    @SerializedName("Created")
    private String created;

    @SerializedName("Predictions")
    private List<Prediction> predictions = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }
}
