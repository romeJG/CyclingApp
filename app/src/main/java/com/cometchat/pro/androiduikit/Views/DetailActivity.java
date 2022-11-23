package com.cometchat.pro.androiduikit.Views;

import static com.cometchat.pro.androiduikit.Helpers.Utils.show;
import static com.cometchat.pro.androiduikit.Helpers.Utils.showInfoDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.cometchat.pro.androiduikit.Helpers.Utils;
import com.cometchat.pro.androiduikit.MainActivity;
import com.cometchat.pro.androiduikit.R;
import com.cometchat.pro.androiduikit.Retrofit.ResponseModel;
import com.cometchat.pro.androiduikit.Retrofit.RestApi;
import com.cometchat.pro.androiduikit.Retrofit.Scientist;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private EditText group_name;
    private TextView nameTV,descriptionTV,galaxyTV,starTV,dobTV,diedTV;
    private FloatingActionButton editFAB;
    private Context c = DetailActivity.this;
    private ProgressBar mProgressBar;
    private Scientist receivedScientist;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String username, email, unique_id;
    private SessionManager sessionManager;

    /**
     * Let's initialize our widgets
     */
    private void initializeWidgets(){
        nameTV= findViewById(R.id.nameTV);
        descriptionTV= findViewById(R.id.descriptionTV);

        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    /**
     * We will now receive and show our data to their appropriate views.
     */
    private void receiveAndShowData(){
        receivedScientist= Utils.receiveScientist(getIntent(),DetailActivity.this);

        if(receivedScientist != null){
            nameTV.setText(receivedScientist.getGroup_name());
            descriptionTV.setText(receivedScientist.getDate());
            mCollapsingToolbarLayout.setTitle(receivedScientist.getName());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(R.color.white));

        }
    }
    /**
     * Let's inflate our menu for the detail page
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_menu, menu);
        return true;
    }

    /**
     * When a menu item is selected we want to navigate to the appropriate page
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        nameTV.setText(receivedScientist.getGroup_name());
        switch (item.getItemId()) {
            case R.id.action_edit:

                username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
                email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
                unique_id = sessionManager.getUserDetail().get(SessionManager.UNIQUE_ID);
                nameTV.setText(receivedScientist.getGroup_name());
                String name;
                String adminTest = "Carl";


                name = nameTV.getText().toString();
                RestApi api = Utils.getClient().create(RestApi.class);
                Call<ResponseModel> createGroup = api.createGroup("JOIN", name, username, email, unique_id);



                createGroup.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call,
                                           Response<ResponseModel> response) {

                        if(response == null || response.body() == null || response.body().getCode()==null){
                            showInfoDialog(DetailActivity.this,"ERROR","Response or Response Body is null. \n Recheck Your PHP code.");
                            return;
                        }

                        Log.d("RETROFIT", "response : " + response.body().toString());
                        String myResponseCode = response.body().getCode();

                        if (myResponseCode.equals("1")) {
                            show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                    + myResponseCode);
                            Utils.openActivity(c, DashboardActivity.class);
                        }else if (myResponseCode.equalsIgnoreCase("2")) {
                            showInfoDialog(DetailActivity.this, "UNSUCCESSFUL",
                                    "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL \n 2. WE"+
                                            " ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                            " \n 3. Most probably the problem is with your PHP Code.");
                        }else if (myResponseCode.equalsIgnoreCase("3")) {
                            showInfoDialog(DetailActivity.this, "NO MYSQL CONNECTION","Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                        }else {
                            Toast.makeText(DetailActivity.this, "You've already joined a group!",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("RETROFIT", "ERROR: " + t.getMessage());
                        showInfoDialog(DetailActivity.this, "FAILURE",
                                "FAILURE THROWN DURING INSERT."+
                                        " ERROR Message: " + t.getMessage());
                    }
                });



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
     * When FAB button is clicked we want to go to the editing page
     */
    @Override
    public void onClick(View v) {
        int id =v.getId();
    }
    /**
     * Let's once again override the attachBaseContext. We do this for our
     * Calligraphy library
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * Let's finish the current activity when back button is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    private void moveToLogin() {
        sessionManager.logoutSession();
        Intent intent = new Intent(DetailActivity.this, Login1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
    /**
     * Our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeWidgets();
        receiveAndShowData();
        sessionManager = new SessionManager(DetailActivity.this); //TINITIGNAN KUNG MAY SESSION
        if(!sessionManager.isLoggedIn()){
            moveToLogin();  ; // REKTA SA LOGIN XML KUNG WALANG SESSION
        }
    }

}
//end
