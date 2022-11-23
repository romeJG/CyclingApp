package com.cometchat.pro.androiduikit.Views;


import static com.cometchat.pro.androiduikit.Helpers.Utils.show;
import static com.cometchat.pro.androiduikit.Helpers.Utils.showInfoDialog;

import android.content.Context;
import android.content.Intent;
import android.helper.DateTimePickerEditText;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.cometchat.pro.androiduikit.Helpers.Utils;
import com.cometchat.pro.androiduikit.R;
import com.cometchat.pro.androiduikit.Retrofit.ResponseModel;
import com.cometchat.pro.androiduikit.Retrofit.RestApi;
import com.cometchat.pro.androiduikit.Retrofit.Scientist;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateGroup extends AppCompatActivity {
    //we'll have several instance fields
    private EditText group_name;
    private DateTimePickerEditText dobTxt, dodTxt;

    private String username, email, unique_id = null;
    private Scientist receivedScientist;
    private Context c = CreateGroup.this;
    private SessionManager sessionManager;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {



        //kukunin na data
        group_name = findViewById(R.id.group_name);

    }
    /**
     * The following method will allow us insert data typed in this page into th
     * e database
     */


    private void createData() {
        String name;

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        unique_id = sessionManager.getUserDetail().get(SessionManager.UNIQUE_ID);

        if (Utils.validate1(group_name)) {
            name = group_name.getText().toString();


            RestApi api = Utils.getClient().create(RestApi.class);
            Call<ResponseModel> createGroup = api.createGroup("CREATE", name, username, email, unique_id);



            createGroup.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call,
                                       Response<ResponseModel> response) {

                    if(response == null || response.body() == null || response.body().getCode()==null){
                        showInfoDialog(CreateGroup.this,"ERROR","Response or Response Body is null. \n Recheck Your PHP code.");
                        return;
                    }

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        Utils.openActivity(c, JoinOrganization.class);
                    }else if (myResponseCode.equalsIgnoreCase("4")){
                        show(c, "Group has already been taken");
                        Utils.openActivity(c, CreateGroup.class);
                    }else if (myResponseCode.equalsIgnoreCase("5")) {
                        show(c, "You've Already have a group");
                        Utils.openActivity(c, CreateGroup.class);
                    }else if (myResponseCode.equalsIgnoreCase("2")) {
                        showInfoDialog(CreateGroup.this, "UNSUCCESSFUL",
                                "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL \n 2. WE"+
                                        " ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    }else if (myResponseCode.equalsIgnoreCase("3")) {
                        showInfoDialog(CreateGroup.this, "NO MYSQL CONNECTION","Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                    }

                }
                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                    showInfoDialog(CreateGroup.this, "FAILURE",
                            "FAILURE THROWN DURING INSERT."+
                                    " ERROR Message: " + t.getMessage());
                }
            });
        }
    }
    /**
     * The following method will allow us update the current scientist's data in the database
     */

    /**
     * When our back button is pressed
     */
    @Override
    public void onBackPressed() {
        showInfoDialog(this, "Warning", "Are you sure you want to exit?");
    }
    /**
     * Let's inflate our menu based on the role this page has been opened for.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedScientist == null) {
            getMenuInflater().inflate(R.menu.new_item_menu, menu);

        } else {
            getMenuInflater().inflate(R.menu.edit_item_menu, menu);

        }
        return true;
    }
    /**
     * Let's listen to menu action events and perform appropriate function
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertMenuItem:
                createData();
                return true;

            case R.id.viewAllMenuItem:
                Utils.openActivity(this, ScientistsActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Attach Base Context
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    /**
     * When our activity is resumed we will receive our data and set them to their editing
     * widgets.
     */

    /**
     * Let's override our onCreate() method
     */
    private void moveToLogin() {
        sessionManager.logoutSession();
        Intent intent = new Intent(CreateGroup.this, Login1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        this.initializeWidgets();

        sessionManager = new SessionManager(CreateGroup.this); //TINITIGNAN KUNG MAY SESSION
        if(!sessionManager.isLoggedIn()){
            moveToLogin();  ; // REKTA SA LOGIN XML KUNG WALANG SESSION
        }
    }
}
//end
