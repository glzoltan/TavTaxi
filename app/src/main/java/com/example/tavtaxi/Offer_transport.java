package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Offer_transport extends AppCompatActivity {
    Calendar c;
    DatePickerDialog dpd;
    Spinner spinnerfrom,spinnerwhere;
    Button btndate,btnother,btnadd,btnmodify;
    TextView phnum,textother;
    EditText  freestates;
    DatabaseReference db;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<String> mCities = new ArrayList<>();
    public static final String SHARED_PREFS="sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_transport);
        btnadd=findViewById(R.id.btnadd);
        btnmodify=findViewById(R.id.btnmodify);
        spinnerfrom = findViewById(R.id.spinnerfrom);
        spinnerwhere = (Spinner)findViewById(R.id.spinnerwhere);
        btndate=findViewById(R.id.button);
        phnum=findViewById(R.id.textphone);
        freestates=findViewById(R.id.editstates);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String activityfrom = sharedPreferences.getString("activityfrom","");
        if(activityfrom.equals("menu"))
        {
            btnmodify.setVisibility(View.VISIBLE);
            btnadd.setVisibility(View.INVISIBLE);
            Intent intent = getIntent();
            btndate.setText(intent.getStringExtra("when"));
            freestates.setText(intent.getStringExtra("states"));
            phnum.setText(intent.getStringExtra("phone"));

        }
        else
        {
            btnmodify.setVisibility(View.INVISIBLE);
            btnadd.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrom.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerwhere.setAdapter(adapter2);

        SharedPreferences sharedPreferences2 = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String phonenum = sharedPreferences2.getString("phonenumber","");
        phnum.setText(phonenum);

        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(Offer_transport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int nYear, int nMonth, int nDay) {
                        btndate.setText(nDay + "/" + (nMonth+1)+"/"+ nYear);
                    }
                },day,month,year);
                dpd.show();

            }
        });
        btnother=findViewById(R.id.btnother);
        textother=findViewById(R.id.textother);

        listItems = getResources().getStringArray(R.array.locations_array);
        checkedItems = new boolean[listItems.length];
        btnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Offer_transport.this);
                mBuilder.setTitle("Intermediate cities");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! mUserItems.contains(position)){
                                mUserItems.add(position);
                            }
                        } else if(!mUserItems.contains(position)){
                            mUserItems.remove(position);
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i=0;i < mUserItems.size();i++){
                            item = item + listItems[mUserItems.get(i)];
                            mCities.add(listItems[mUserItems.get(i)]);
                            if(i != mUserItems.size()-1){
                                item= item + ", ";
                            }
                        }
                        textother.setText(item);
                    }
                });
                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i < checkedItems.length;i++){
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mCities.clear();
                            textother.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btndate.getText().equals("Pick a date")){
                    Toast.makeText(getApplicationContext() , "PLEASE CHOSE A DATE", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = FirebaseDatabase.getInstance().getReference("trips");
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String username = sharedPreferences.getString("username","");
                String id=db.push().getKey();
                String from=spinnerfrom.getSelectedItem().toString();
                String where=spinnerwhere.getSelectedItem().toString();
                String when=btndate.getText().toString();
                String freests=freestates.getText().toString();
                String phonenum=phnum.getText().toString();
                Fire_Trip trip= new Fire_Trip(id,username,from,where,when,freests,mCities,phonenum);
                db.child(id).setValue(trip);
                Toast.makeText(getApplicationContext() , "TRIP IS ADDED!", Toast.LENGTH_SHORT).show();

            }
        });
        btnmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btndate.getText().equals("Pick a date")){
                    Toast.makeText(getApplicationContext() , "PLEASE CHOSE A DATE", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = FirebaseDatabase.getInstance().getReference("trips");
                Intent intent = getIntent();
                String id=intent.getStringExtra("id");
                String username=intent.getStringExtra("username");
                String from=spinnerfrom.getSelectedItem().toString();
                String where=spinnerwhere.getSelectedItem().toString();
                String when=btndate.getText().toString();
                String freests=freestates.getText().toString();
                String phonenum=phnum.getText().toString();
                Fire_Trip trip= new Fire_Trip(id,username,from,where,when,freests,mCities,phonenum);
                db.child(id).setValue(trip);
                Toast.makeText(getApplicationContext() , "TRIP IS MODIFIED!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
