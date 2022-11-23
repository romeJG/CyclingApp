package com.cometchat.pro.androiduikit;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cometchat.pro.androiduikit.Views.DashboardActivity;
import com.cometchat.pro.androiduikit.Views.Login1;
import com.cometchat.pro.androiduikit.Views.SessionManager;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.cometchat.pro.androiduikit.directionhelpers.TaskLoadedCallback;
import com.cometchat.pro.androiduikit.ui.gpstracker.GpsTrackerFragment;

/**
 * MainActivity
 */
public class MainActivityTrackingNavigation extends AppCompatActivity implements TaskLoadedCallback {
    String access_code, name;
    TextView accessCode1;
    private SessionManager sessionManager;

    /**
     * Set up bottom navigation and display default fragment view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accessCode1 = findViewById(R.id.accessCode);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


    }

    /**
     * onTaskDone callback method from TaskLoadedCallback, used as direction helper
     * Add polyline of route to map
     * @param values
     */
    @Override
    public void onTaskDone(Object... values) {
        if(GpsTrackerFragment.currentPolyline != null){
            GpsTrackerFragment.currentPolyline.remove();
            GpsTrackerFragment.currentPolyline = GpsTrackerFragment.mMap.addPolyline((PolylineOptions)values[0]);
        } else{
            GpsTrackerFragment.currentPolyline = GpsTrackerFragment.mMap.addPolyline((PolylineOptions)values[0]);
        }
    }


}
