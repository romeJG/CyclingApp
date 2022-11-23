package com.cometchat.pro.androiduikit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.androiduikit.R;
import com.cometchat.pro.androiduikit.apiLogin.ApiClient;
import com.cometchat.pro.androiduikit.apiLogin.ApiInterface;
import com.cometchat.pro.androiduikit.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername, etPassword, etName, etRegisterEmail, etRegisterLastName, etRegisterPasswordSecond;
    Button btnRegister;
    TextView tvLogin;
    String Username, Password, Name, Lname, Email, Second_Password;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegisterUsername);
        etPassword = findViewById(R.id.etRegisterPassword);
        etName = findViewById(R.id.etRegisterName);

        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterLastName = findViewById(R.id.etRegisterLastName);
        etRegisterPasswordSecond = findViewById(R.id.etRegisterPasswordSecond);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        tvLogin = findViewById(R.id.tvLoginHere);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRegister:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                Name = etName.getText().toString();
                Lname = etRegisterLastName.getText().toString();
                Email = etRegisterEmail.getText().toString();
                Second_Password = etRegisterPasswordSecond.getText().toString();

                register(Username, Password, Name, Lname, Email, Second_Password);
                break;

            case R.id.tvLoginHere:
                Intent intent = new Intent(this, Login1.class);
                startActivity(intent);
                finish();
                break;


        }

    }

    private void register(String username, String password, String name, String lname, String email, String second_password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> call = apiInterface.registerResponse(username, password, name, lname, email, second_password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, Login1.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}