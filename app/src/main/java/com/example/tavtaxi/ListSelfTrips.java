package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSelfTrips extends AppCompatActivity {


    RecyclerView recyclerView;
    SelfTripsAdapter adapter;
    private DatabaseReference mDatabase;

    List<Fire_Trip> selfTripList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_self_trips);

        selfTripList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSelfTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Fire_Trip newTrip = new Fire_Trip("id","Here","There","When",null,null,null);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Trips").push().setValue(newTrip);
        mDatabase.child("Trips").orderByKey().addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // for example: if you're expecting your user's data as an object of the "User" class.
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Fire_Trip self_trip = ds.getValue(Fire_Trip.class);
                            String from = self_trip.getFROM();
                            String where = self_trip.getWHERE();
                            String when = self_trip.getWHEN();
                            selfTripList.add(new Fire_Trip("id", from, where, when,null,null,null));
                            adapter = new SelfTripsAdapter(ListSelfTrips.this, selfTripList);
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
