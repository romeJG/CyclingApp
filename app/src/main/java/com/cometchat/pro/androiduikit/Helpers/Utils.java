package com.cometchat.pro.androiduikit.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.androiduikit.R;
import com.cometchat.pro.androiduikit.Retrofit.Scientist;
import com.cometchat.pro.androiduikit.Views.DashboardActivity;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A Utility class. Contains reusable utility methods we will use throughout our project.This
 * class will save us from typing lots of repetitive code.
 */
public class Utils {
    /**
     * Let's define some Constants
     */
    //supply your ip address. Type ipconfig while connected to internet to get your
    //ip address in cmd. Watch video for more details.
    //private  static  final String base_url = "http://192.168.43.91/PHP/scientists/";
    //private  static  final String base_url = "https://192.168.56.1/PHP/scientists/";
    private  static  final String base_url = "https://sinovaccycling.com/mobile_app/";
    //private  static  final String base_url = "http://10.0.3.2/PHP/scientists/";
    private static Retrofit retrofit;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * This method will return us our Retrofit instance which we can use to initiate HTTP calls.
     */
    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retrofit;
    }
    /**
     * THis method will allow us show Toast messages throughout all activities
     */
    public static void show(Context c,String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * This method will allow us validate edittexts
     */
    public static boolean validate(EditText... editTexts){
        EditText nameTxt = editTexts[0];
        EditText descriptionTxt = editTexts[1];
        EditText galaxyTxt = editTexts[2];

        if(nameTxt.getText() == null || nameTxt.getText().toString().isEmpty()){
            nameTxt.setError("Name is Required Please!");
            return false;
        }
        if(descriptionTxt.getText() == null || descriptionTxt.getText().toString().isEmpty()){
            descriptionTxt.setError("Description is Required Please!");
            return false;
        }
        if(galaxyTxt.getText() == null || galaxyTxt.getText().toString().isEmpty()){
            galaxyTxt.setError("Galaxy is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validate1(EditText... editTexts){
        EditText group_name = editTexts[0];

        if(group_name.getText() == null || group_name.getText().toString().isEmpty()){
            group_name.setError("Name is Required Please!");
            return false;
        }

        return true;

    }
    /**
     * This utility method will allow us clear arbitrary number of edittexts
     */
    public static void clearEditTexts(EditText... editTexts){
        for (EditText editText:editTexts) {
            editText.setText("");
        }
    }
    /**
     * This utility method will allow us open any activity.
     */
    public static void openActivity(Context c,Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }
    /**
     * This method will allow us show an Info dialog anywhere in our app.
     */
    public static void showInfoDialog(final AppCompatActivity activity, String title,
                                      String message) {
        new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.indigo)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.flip_page)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Relax", v -> {})
                .setNeutralButton("Go Home", v -> openActivity(activity, DashboardActivity.class))
                .setNegativeButton("Go Back", v -> activity.finish())
                .show();
    }
    /**
     * This method will allow us show a single select dialog where we can select and return a
     * star to an edittext.
     */
    public static void selectStar(Context c,final EditText starTxt){
        String[] stars ={"Rigel","Aldebaran","Arcturus","Betelgeuse","Antares","Deneb",
                "Wezen","VY Canis Majoris","Sirius","Alpha Pegasi","Vega","Saiph","Polaris",
                "Canopus","KY Cygni","VV Cephei","Uy Scuti","Bellatrix","Naos","Pollux",
                "Achernar","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                stars);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Stars Picker")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setMessage("Select the Star where the Scientist was born.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> starTxt.setText(item))
                .show();
    }

    /**
     * This method will allow us convert a string into a java.util.Date object and
     *  return it.
     */
    public static Date giveMeDate(String stringDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * This method will allow us show a progressbar
     */
    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }
    /**
     * This method will allow us hide a progressbar
     */

    /**
     * This method will allow us send a serialized scientist objec  to a specified
     *  activity
     */
    public static void sendScientistToActivity(Context c, Scientist scientist,
                                               Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("SCIENTIST_KEY",scientist);
        c.startActivity(i);
    }

    /**
     * This method will allow us receive a serialized scientist, deserialize it and return it,.
     */
    public  static Scientist receiveScientist(Intent intent,Context c){
        try {
            return (Scientist) intent.getSerializableExtra("SCIENTIST_KEY");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
//end
