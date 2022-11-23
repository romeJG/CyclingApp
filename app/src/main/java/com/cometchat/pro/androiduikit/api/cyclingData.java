package com.cometchat.pro.androiduikit.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cyclingData {


    @Expose
    @SerializedName("img") private String img;
    @Expose
    @SerializedName("timestamp") private String timestamp;
    @Expose
    @SerializedName("avgSpeedinKMH") private Float avgSpeedinKMH;
    @Expose
    @SerializedName("distanceInMeters") private int distanceInMeters;
    @Expose
    @SerializedName("timeInMillis") private String timeInMillis;
    @Expose
    @SerializedName("caloriesBurned") private int caloriesBurned;
    @Expose
    @SerializedName("unique_id") private int unique_id;

    public void setImg(String img) {
        this.img = img;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setAvgSpeedinKMH(Float avgSpeedinKMH) {
        this.avgSpeedinKMH = avgSpeedinKMH;
    }

    public void setDistanceInMeters(int distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public void setTimeInMillis(String timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public void setUnique_id(int unique_id) {
        this.unique_id = unique_id;
    }
}