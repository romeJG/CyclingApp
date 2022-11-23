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
import com.cometchat.pro.androiduikit.model.mes.Mes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Message extends AppCompatActivity implements View.OnClickListener{
    EditText subject, message;
    Button btnSubmitFeed;
    ApiInterface apiInterface;
    TextView goBack;
    SessionManager sessionManager;
    String username, subject_test, message_test;
    TextView etUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        sessionManager = new SessionManager(Message.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etUsername = findViewById(R.id.etMainUsername);

        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        btnSubmitFeed = findViewById(R.id.btnSubmitFeed);
        btnSubmitFeed.setOnClickListener(this);

        //SET THE USER'S SESSION
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        etUsername.setText(username);




    }
    private void moveToLogin() {
        Intent intent = new Intent(Message.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmitFeed:

                subject_test = subject.getText().toString();
                message_test = message.getText().toString();
                createGroup(username, subject_test, message_test);
                break;
        }
    }

    private void createGroup(String username, String subject, String message) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Mes> call = apiInterface.messageResponse(username, subject, message);
        call.enqueue(new Callback<Mes>() {

            @Override
            public void onResponse(Call<Mes> call, Response<Mes> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(Message.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Message.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Message.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mes> call, Throwable t) {
                Toast.makeText(Message.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}