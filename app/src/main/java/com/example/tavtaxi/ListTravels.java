package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
    private DatabaseReference databaseReference;
    public static final String SHARED_PREFS="sharedPrefs";
    List<Fire_Trip> tripList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_travels);

        tripList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("trips").orderByKey().addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // for example: if you're expecting your user's data as an object of the "User" class.
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Fire_Trip trips = ds.getValue(Fire_Trip.class);
                            String from = trips.getFrom();
                            String where = trips.getWhere();
                            String when = trips.getWhen();
                            String tripID=trips.getId();
                            String freestates = trips.getFreeStates();
                            String phone=trips.getPhoneNumber();
                            ArrayList<String> cities=trips.getCities();
                            Intent intent = getIntent();
                            String sfrom = intent.getStringExtra("from");
                            String swhere = intent.getStringExtra("where");
                            String swhen = intent.getStringExtra("when");

                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                            String activityfrom= sharedPreferences.getString("activityfrom","");
                            String phoneuser=sharedPreferences.getString("phonenumber","");
                            String username=sharedPreferences.getString("username","");
                            if(activityfrom.equals("search")){
                                if((from.equals(sfrom) && where.equals(swhere) && when.equals(swhen))||
                                        (from.equals(sfrom) && cities.contains(swhere) && when.equals(swhen))||
                                        (cities.contains(sfrom) && where.equals(swhere) && when.equals(swhen))) {
                                    tripList.add(new Fire_Trip(tripID,username, from, where, when, freestates, cities, phone));
                                }
                            }
                            else{

                                if(phone.equals(phoneuser)){
                                    tripList.add(new Fire_Trip(tripID, username,from, where, when, freestates, cities, phone));
                                }
                            }

                            adapter = new TripsAdapter(ListTravels.this, tripList);
                            recyclerView.setAdapter(adapter);


                        }
                        if (tripList.size()==0){
                            Toast.makeText(getApplicationContext(),"No trips to show",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.v("List travels activity: " ,"Database error" );
                    }
                });
    }
}
