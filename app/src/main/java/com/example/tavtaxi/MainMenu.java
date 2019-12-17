package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button openSearchButton, openOfferButton, openMyTripsButton;
    public static final String SHARED_PREFS="sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        openSearchButton =findViewById(R.id.searchButton);
        openOfferButton =findViewById(R.id.offerButton);
        openMyTripsButton =findViewById(R.id.myTripsButton);
        openSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search_transport.class);
                startActivity(intent);

            }
        });
        openOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Offer_transport.class);
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("activityfrom","search");
                editor.apply();
                startActivity(intent);

            }
        });
        openMyTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListTravels.class);
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("activityfrom","menu");
                editor.apply();
                startActivity(intent);

            }
        });

    }

    public void logOutOnClick(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainMenu.this);
        alertBuilder.setMessage("Are you sure you want to log out?");
        alertBuilder.setCancelable(true);

        alertBuilder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editor.putBoolean("checked", false);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        alertBuilder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertBuilder.create();
        alert11.show();

    }
}
