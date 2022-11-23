package com.cometchat.pro.androiduikit.Views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cometchat.pro.androiduikit.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    Polyline lastPolyline;
    boolean isSecond = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

    }



    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);








        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                        Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                    Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }



                //move map camera
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                class NearbySearch {

                    public PlacesSearchResponse run(){
                        PlacesSearchResponse request = new PlacesSearchResponse();
                        GeoApiContext context = new GeoApiContext.Builder()
                                .apiKey("AIzaSyBmoN6ATpbE6_tUv8s4AVA7CSvK8pLeSUM")
                                .build();
                        com.google.maps.model.LatLng location = new com.google.maps.model.LatLng(latLng.latitude, latLng.longitude);

                        try {
                            request = PlacesApi.nearbySearchQuery(context, location)
                                    .radius(5000)
                                    .rankby(RankBy.PROMINENCE)
                                    .type(PlaceType.BICYCLE_STORE)
                                    .await();
                        } catch (ApiException | IOException | InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            return request;
                        }
                    }
                }






                PlacesSearchResult[] placesSearchResults = new NearbySearch().run().results;



                if(placesSearchResults != null){
                    Log.e("response1Tag", placesSearchResults[0].toString());
                    Log.e("response2Tag", placesSearchResults[1].toString());
                    Log.e("response3Tag", placesSearchResults[2].toString());
                    Log.e("response4Tag", placesSearchResults[3].toString());
                    Log.e("response5Tag", placesSearchResults[4].toString());

                    double lat1 = placesSearchResults[0].geometry.location.lat;
                    double lng1 = placesSearchResults[0].geometry.location.lng;

                    double lat2 = placesSearchResults[1].geometry.location.lat;
                    double lng2 = placesSearchResults[1].geometry.location.lng;

                    double lat3 = placesSearchResults[2].geometry.location.lat;
                    double lng3 = placesSearchResults[2].geometry.location.lng;

                    double lat4 = placesSearchResults[3].geometry.location.lat;
                    double lng4 = placesSearchResults[3].geometry.location.lng;

                    double lat5 = placesSearchResults[4].geometry.location.lat;
                    double lng5 = placesSearchResults[4].geometry.location.lng;

                    mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat1, lng1))
                            .title("Bike Repair Shop"));
                    mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lng2))
                            .title("Bike Repair Shop"));

                    mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat3, lng3))
                            .title("Bike Repair Shop"));

                    mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat4, lng4))
                            .title("Bike Repair Shop"));

                    mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat5, lng5))
                            .title("Bike Repair Shop"));



                    mGoogleMap.setMinZoomPreference(14.0f);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat1, lng1)));

                }else {
                    Toast.makeText(MapsActivity.this, "No nearby places!", Toast.LENGTH_LONG).show();
                }


            }
        }
    };


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    // if not allow a permission, the application will exit
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
            }
        }
    }

}
