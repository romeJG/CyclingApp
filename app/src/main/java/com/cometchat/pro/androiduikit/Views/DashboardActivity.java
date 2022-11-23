package com.cometchat.pro.androiduikit.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.androiduikit.BaseApplcation;
import com.cometchat.pro.androiduikit.MainActivityTrackingNavigation;
import com.cometchat.pro.androiduikit.challenges.challenges;
import com.cometchat.pro.androiduikit.ui.MainActivity;
import com.cometchat.pro.androiduikit.ui.fragments.SetupFragment;
import com.cometchat.pro.androiduikit.CreateUserActivity;
import com.cometchat.pro.androiduikit.Helpers.Utils;
import com.cometchat.pro.androiduikit.R;


import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DashboardActivity extends AppCompatActivity {

    //We have 4 cards in the dashboard

    LinearLayout addScientistCard;
    LinearLayout closeCard;
    LinearLayout joinGroup;
    LinearLayout viewLeader;
    LinearLayout viewFeedback;
    LinearLayout viewLocation;
    LinearLayout viewMessage;
    SessionManager sessionManager;
    LinearLayout viewRecord;
    LinearLayout myProfile;
    LinearLayout trackingWithNav;
    LinearLayout challenges;
    LinearLayout viewTips;

    /**
     * Let's initialize our cards  and listen to their click events
     */
    private void initializeWidgets(){

        addScientistCard = findViewById(R.id.addScientistCard);
        joinGroup = findViewById(R.id.joinGroup);
        closeCard = findViewById(R.id.closeCard);
        viewLocation = findViewById(R.id.viewLocation);
        viewMessage = findViewById(R.id.viewMessage);
        myProfile = findViewById(R.id.myProfile);
        trackingWithNav = findViewById(R.id.trackingWithNav);
        challenges = findViewById(R.id.challenges_dashboard);
        viewTips = findViewById(R.id.viewTips);



        trackingWithNav.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this, MainActivityTrackingNavigation.class));
        myProfile.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this, MyProfile.class));
        viewMessage.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this, CreateUserActivity.class));
        viewLocation.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,MapsActivity.class));
        joinGroup.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,JoinOrganization.class));
        challenges.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this, com.cometchat.pro.androiduikit.challenges.challenges.class));
        viewTips.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this, com.cometchat.pro.androiduikit.Views.MainActivity.class));
        addScientistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
            public void openActivity2(){
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        closeCard.setOnClickListener(v -> moveToLogin());

        sessionManager = new SessionManager(DashboardActivity.this); //TINITIGNAN KUNG MAY SESSION
        if(!sessionManager.isLoggedIn()){
            moveToLogin();  ; // REKTA SA LOGIN XML KUNG WALANG SESSION
        }

    }
    /**
     * Let's override the attachBaseContext() method
     */

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    /**
     * When the back button is pressed finish this activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * Let's override the onCreate() and call our initializeWidgets()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.initializeWidgets();
    }
    private void moveToLogin() {
        sessionManager.logoutSession();
        Intent intent = new Intent(DashboardActivity.this, Login1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

}

