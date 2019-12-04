package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

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
    private DatabaseReference mDatabase;

    List<Fire_Trip> tripList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_travels);

        tripList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Fire_Trip newTrip = new Fire_Trip("id","Here","There","When", "Freestates");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Trips").push().setValue(newTrip);
        mDatabase.child("Trips").orderByKey().addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // for example: if you're expecting your user's data as an object of the "User" class.
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Fire_Trip trips = ds.getValue(Fire_Trip.class);
                            String from = trips.getFROM();
                            String where = trips.getWHERE();
                            String when = trips.getWHEN();
                            String freestates = trips.getFreeStates();
                            tripList.add(new Fire_Trip("id", from, where, when,freestates));
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
