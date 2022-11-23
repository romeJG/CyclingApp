package com.cometchat.pro.androiduikit.api;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveToSql {


     public static ApiInterfaceBike apiInterfaceBike;

    public static void saveToSQL(
            String img,
            String dateTimestamp,
            Float avgSpeed,
            int distanceInMeters,
            String curTimeInMillis,
            int caloriesBurned,
            String unique_id
    ){
        apiInterfaceBike = ApiClientBike.getApiClient().create(ApiInterfaceBike.class);
        Call<cyclingData> call = apiInterfaceBike.saveCyclingData(img, dateTimestamp, avgSpeed, distanceInMeters, curTimeInMillis, caloriesBurned, unique_id);
        call.enqueue(new Callback<cyclingData>() {
            @Override
            public void onResponse(Call<cyclingData> call, Response<cyclingData> response) {
            }
            @Override
            public void onFailure(Call<cyclingData> call, Throwable t) {
            }
        });
    }
}

