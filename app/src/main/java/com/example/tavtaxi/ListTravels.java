package com.example.tavtaxi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListTravels extends AppCompatActivity {
    RecyclerView recyclerView;
    TripsAdapter adapter;
    TextView etNumber;
    private DatabaseReference mDatabase;

    List<Fire_Trip> tripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_travels);
        etNumber = findViewById(R.id.textPhone);
        tripList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("trips").orderByKey().addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // for example: if you're expecting your user's data as an object of the "User" class.
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Fire_Trip trips = ds.getValue(Fire_Trip.class);
                            String from = trips.getFrom();
                            String where = trips.getWhere();
                            String when = trips.getWhen();
                            String freestates = trips.getFreeStates();
                            String phone = trips.getPhoneNumber();
                            ArrayList<String> cities = trips.getCities();
                            tripList.add(new Fire_Trip("id", from, where, when, freestates, cities, phone));
                            adapter = new TripsAdapter(ListTravels.this, tripList);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // read query is cancelled.
                    }
                });

    }
}
