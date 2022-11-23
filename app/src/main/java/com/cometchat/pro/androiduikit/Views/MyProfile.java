package com.cometchat.pro.androiduikit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.androiduikit.Helpers.Utils;
import com.cometchat.pro.androiduikit.R;

public class MyProfile extends AppCompatActivity {
    private SessionManager sessionManager;
    private String username, email, subscription, group;
    Button settings, record;
    TextView username_text, email_text, group_text, renew, rate;
    ImageView profile_picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        username_text = findViewById(R.id.username);
        email_text = findViewById(R.id.email);
        group_text = findViewById(R.id.group);
        settings = findViewById(R.id.settings);
        record = findViewById(R.id.record);
        renew = findViewById(R.id.renew);
        rate = findViewById(R.id.rate);


        rate.setOnClickListener(v -> Utils.openActivity(MyProfile.this, Message.class));
        record.setOnClickListener(v -> Utils.openActivity(MyProfile.this, ChangeInfo.class));
        settings.setOnClickListener(v -> Utils.openActivity(MyProfile.this, ChangePassword.class));

        sessionManager = new SessionManager(MyProfile.this); //TINITIGNAN KUNG MAY SESSION
        if(!sessionManager.isLoggedIn()){
            moveToLogin(); //REKTA SA LOGIN XML KUNG WALANG SESSION
        }

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        subscription = sessionManager.getUserDetail().get(SessionManager.SUBSCRIPTION);
        group = sessionManager.getUserDetail().get(SessionManager.GROUP);


        username_text.setText(username);
        email_text.setText(email);
        renew.setText("Subscription Renewal: " + subscription);
        group_text.setText(group);


    }

    private void moveToLogin() {
        sessionManager.logoutSession();
        Intent intent = new Intent(MyProfile.this, Login1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}