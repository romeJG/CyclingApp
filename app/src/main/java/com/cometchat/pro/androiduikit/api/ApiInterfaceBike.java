package com.cometchat.pro.androiduikit.api;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceBike {

    @FormUrlEncoded
    @POST("save.php")
    Call<cyclingData> saveCyclingData(
            @Field("img") String img,
            @Field("timestamp") String timestamp,
            @Field("avgSpeedinKMH") Float avgSpeedinKMH,
            @Field("distanceInMeters") int distanceInMeters,
            @Field("timeInMillis") String timeInMillis,
            @Field("caloriesBurned") int caloriesBurned,
            @Field("unique_id") String unique_id
            );
}

