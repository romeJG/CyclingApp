package com.cometchat.pro.androiduikit.apiLogin;



import com.cometchat.pro.androiduikit.model.change.Change;
import com.cometchat.pro.androiduikit.model.changeInfo.Profile;
import com.cometchat.pro.androiduikit.model.create.Create;
import com.cometchat.pro.androiduikit.model.login.Login;
import com.cometchat.pro.androiduikit.model.mes.Mes;
import com.cometchat.pro.androiduikit.model.register.Register;
import com.cometchat.pro.androiduikit.model.subscribe.Subscribe;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("feedback.php")
    Call<Mes> messageResponse(
            @Field("username") String username,
            @Field("subject") String subject,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("changepass.php")
    Call<Change> changeResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("first_password") String first_password,
            @Field("second_password") String second_password

    );

    @FormUrlEncoded
    @POST("profile.php")
    Call<Create> createResponse(
            @Field("username") String username,
            @Field("admin") String admin
    );
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("second_password") String second_password

    );

    @FormUrlEncoded
    @POST("subscribe.php")
    Call<Subscribe> subscribeResponse(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("information.php")
    Call<Profile> informationResponse(
            @Field("username") String username,
            @Field("email") String email,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("unique_id") String unique_id

    );




}
