package com.example.maju.mazdor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtils {

    public SharedPreferencesUtils() {
        super();
    }


    public void setUserName(Context context, String favorites) {
        SharedPreferences settings = context.getSharedPreferences("USER_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USER_NAME", favorites);
        editor.apply();
    }

    public void setUserEmail(Context context, String favorites) {
        SharedPreferences settings = context.getSharedPreferences("USER_EMAIL", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USER_EMAIL", favorites);
        editor.apply();
    }

    public void setUserPhone(Context context, String favorites) {
        SharedPreferences settings = context.getSharedPreferences("USER_PHONE", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USER_PHONE", favorites);
        editor.apply();
    }

    public void setUserDesignation(Context context, String favorites) {
        SharedPreferences settings = context.getSharedPreferences("USER_DESIGNATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USER_DESIGNATION", favorites);
        editor.apply();
    }


    public String getUserName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_NAME", MODE_PRIVATE);
        return prefs.getString("USER_NAME", "");
    }
    public String getUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_EMAIL", MODE_PRIVATE);
        return prefs.getString("USER_EMAIL", "");
    }
    public String getUserPhone(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_PHONE", MODE_PRIVATE);
        return prefs.getString("USER_PHONE", "");
    }
    public String getUserDesignation(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_DESIGNATION", MODE_PRIVATE);
        return prefs.getString("USER_DESIGNATION", "");
    }

}
