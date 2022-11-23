package com.cometchat.pro.androiduikit.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cometchat.pro.androiduikit.model.login.LoginData;

import java.util.HashMap;


public class SessionManager {

    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String UNIQUE_ID = "unique_id";
    public static final String ACCESS = "access";
    public static final String SUBSCRIPTION = "subscription";
    public static final String GROUP = "group";

    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getUserId());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(NAME, user.getName());
        editor.putString(PASSWORD, user.getPassword());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(UNIQUE_ID, user.getUnique_id());
        editor.putString(ACCESS, user.getAccess());
        editor.putString(SUBSCRIPTION, user.getSubscription());
        editor.putString(GROUP, user.getGroup());

        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID,null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME,null));
        user.put(NAME, sharedPreferences.getString(NAME,null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD,null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        user.put(UNIQUE_ID, sharedPreferences.getString(UNIQUE_ID,null));
        user.put(ACCESS, sharedPreferences.getString(ACCESS,null));
        user.put(SUBSCRIPTION, sharedPreferences.getString(SUBSCRIPTION,null));
        user.put(GROUP, sharedPreferences.getString(GROUP,null));
        return user;
    }

    public void logoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
