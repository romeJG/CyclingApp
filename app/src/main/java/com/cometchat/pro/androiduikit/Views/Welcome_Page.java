package com.cometchat.pro.androiduikit.Views;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.androiduikit.CheckoutActivityJava;
import com.cometchat.pro.androiduikit.R;

public class Welcome_Page extends AppCompatActivity {
    Button btnSubmitSubs, btnMainFeature;
    SessionManager sessionManager;
    String access_code, name;
    TextView accessCode1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__page);
        accessCode1 = findViewById(R.id.accessCode);

        sessionManager = new SessionManager(Welcome_Page.this); //TINITIGNAN KUNG MAY SESSION
        if(!sessionManager.isLoggedIn()){
            moveToLogin();  ; // REKTA SA LOGIN XML KUNG WALANG SESSION
        }

        access_code = sessionManager.getUserDetail().get(SessionManager.ACCESS); //0
        //1 == 1 papasok
        //0 == 1 di papasok

        int number = parseInt(access_code);

        Button button= (Button) findViewById(R.id.btnMainFeature);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(number == 1){
                    startActivity(new Intent(Welcome_Page.this,DashboardActivity.class));
                }else{
                    Toast.makeText(Welcome_Page.this,"Account isn't subscribed yet!",Toast.LENGTH_LONG).show();
                }

            }
        });

        Button button1 = (Button) findViewById(R.id.btnSubmitSubs);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(number == 1){
                    Toast.makeText(Welcome_Page.this,"You already subscribed!",Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(Welcome_Page.this,CheckoutActivityJava.class));
                }

            }
        });

    }

    private void moveToLogin() {
        sessionManager.logoutSession();
        Intent intent = new Intent(Welcome_Page.this, Login1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}