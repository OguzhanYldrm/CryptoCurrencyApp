package com.example.cryptocurrencyapp.helper;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class DialogHelper {
    public static AlertDialog getAlertWithMessage(String title, String message, Context context){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setTitle(title);
        builder1.setCancelable(true);
        builder1.setPositiveButton("TAMAM", null);

        AlertDialog alertDialog = builder1.create();

        return alertDialog;

    }
}
