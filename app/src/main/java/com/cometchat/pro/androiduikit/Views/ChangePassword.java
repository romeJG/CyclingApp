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
import com.cometchat.pro.androiduikit.model.change.Change;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePassword extends AppCompatActivity implements View.OnClickListener{
    EditText firstPassword, secondPassword, password;
    Button btnLoginCreate;
    ApiInterface apiInterface;
    String Group_Name;
    SessionManager sessionManager;
    String username, Password, First_Password, Second_Password;
    TextView etUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sessionManager = new SessionManager(ChangePassword.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etUsername = findViewById(R.id.etMainUsername);

        password = findViewById(R.id.password);
        firstPassword = findViewById(R.id.firstPassword);
        secondPassword = findViewById(R.id.secondPassword);
        btnLoginCreate = findViewById(R.id.btnLoginCreate);
        btnLoginCreate.setOnClickListener(this);

        //SET THE USER'S SESSION
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        etUsername.setText(username);




    }
    private void moveToLogin() {
        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginCreate:

                Password = password.getText().toString();
                First_Password = firstPassword.getText().toString();
                Second_Password = secondPassword.getText().toString();
                createGroup(username, Password, First_Password, Second_Password);
                break;
        }
    }

    private void createGroup(String username, String password, String first_password, String second_password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Change> call = apiInterface.changeResponse(username, password,first_password,second_password);
        call.enqueue(new Callback<Change>() {

            @Override
            public void onResponse(Call<Change> call, Response<Change> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePassword.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Change> call, Throwable t) {
                Toast.makeText(ChangePassword.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}