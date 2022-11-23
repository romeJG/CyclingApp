package com.cometchat.pro.androiduikit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.androiduikit.MainActivityTrackingNavigation;
import com.cometchat.pro.androiduikit.R;
import com.cometchat.pro.androiduikit.apiLogin.ApiClient;
import com.cometchat.pro.androiduikit.apiLogin.ApiInterface;
import com.cometchat.pro.androiduikit.model.login.Login;
import com.cometchat.pro.androiduikit.model.login.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login1 extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button btnLogin, btnLoginCreate;
    String Username, Password;
    TextView tvRegister;
    ApiInterface apiInterface;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);


        tvRegister = findViewById(R.id.tvCreateAccount);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                login(Username,Password);
                break;
            case R.id.tvCreateAccount:
                Intent intent = new Intent(this,  RegisterActivity.class);
                startActivity(intent);
                break;


        }
    }

    private void login(String username, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> loginCall = apiInterface.loginResponse(username,password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                    //This is to save the session
                    sessionManager = new  SessionManager(Login1.this);
                    LoginData loginData = response.body().getLoginData();
                    sessionManager.createLoginSession(loginData);

                    //This is for moving
                    Toast.makeText(Login1.this, response.body().getLoginData().getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login1.this, MainActivityTrackingNavigation.class); //Papuntang dashboard activity
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(Login1.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}