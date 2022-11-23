package com.cometchat.pro.androiduikit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.androiduikit.LoginActivity;
import com.cometchat.pro.androiduikit.R;
import com.cometchat.pro.androiduikit.apiLogin.ApiClient;
import com.cometchat.pro.androiduikit.apiLogin.ApiInterface;
import com.cometchat.pro.androiduikit.model.changeInfo.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangeInfo extends AppCompatActivity implements View.OnClickListener{

    Button btnLoginCreate;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    String username, user_text, email_text, fname, lname, unique_id;
    TextView etUsername, username_text, email, first_name, last_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        sessionManager = new SessionManager(ChangeInfo.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etUsername = findViewById(R.id.etMainUsername);
        username_text = findViewById(R.id.username);
        email = findViewById(R.id.email);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);


        btnLoginCreate = findViewById(R.id.btnLoginCreate);
        btnLoginCreate.setOnClickListener(this);


        //SET THE USER'S SESSION
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email_text = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        unique_id = sessionManager.getUserDetail().get(SessionManager.UNIQUE_ID);

        username_text.setText((username));
        email.setText((email_text));






    }
    private void moveToLogin() {
        Intent intent = new Intent(ChangeInfo.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginCreate:

                user_text =  username_text.getText().toString();
                email_text = email.getText().toString();
                fname = first_name.getText().toString();
                lname = last_name.getText().toString();

                createGroup(username, email_text, fname, lname, unique_id);
                break;
        }
    }

    private void createGroup(String username, String email, String first_name, String last_name, String unique_id) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Profile> call = apiInterface.informationResponse(username, email, first_name, last_name, unique_id);
        call.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(ChangeInfo.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeInfo.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ChangeInfo.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(ChangeInfo.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}