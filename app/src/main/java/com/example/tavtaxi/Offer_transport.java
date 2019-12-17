package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    Spinner spinnerfrom,spinnerwhere;
    Button dateButton, otherButton, addButton, modifyButton;
    TextView phoneNumTextView, otherTextView;
    EditText freeSeats;
    String spinnerTextFrom;
    String spinnerTextTo;
    DatabaseReference databaseReference;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> userItems = new ArrayList<>();
    ArrayList<String> cities = new ArrayList<>();
    public static final String SHARED_PREFS="sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_transport);
        addButton =findViewById(R.id.btnadd);
        modifyButton =findViewById(R.id.btnmodify);
        spinnerfrom = findViewById(R.id.spinnerfrom);
        spinnerwhere = findViewById(R.id.spinnerwhere);
        dateButton =findViewById(R.id.button);
        phoneNumTextView =findViewById(R.id.textphone);
        freeSeats =findViewById(R.id.editstates);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String activityfrom = sharedPreferences.getString("activityfrom","");
        if(activityfrom.equals("menu"))
        {
            modifyButton.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.INVISIBLE);
            Intent intent = getIntent();
            dateButton.setText(intent.getStringExtra("when"));
            freeSeats.setText(intent.getStringExtra("states"));
            phoneNumTextView.setText(intent.getStringExtra("phone"));

            spinnerTextFrom = intent.getStringExtra("from");
            spinnerTextTo = intent.getStringExtra("where");




        }
        else
        {
            modifyButton.setVisibility(View.INVISIBLE);
            addButton.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrom.setAdapter(adapterFrom);

        ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerwhere.setAdapter(adapterTo);

        selectSpinnerValue(spinnerfrom,spinnerTextFrom);
        selectSpinnerValue(spinnerwhere,spinnerTextTo);

        SharedPreferences sharedPreferences2 = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String phonenum = sharedPreferences2.getString("phonenumber","");
        phoneNumTextView.setText(phonenum);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Offer_transport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int nYear, int nMonth, int nDay) {
                        dateButton.setText(nDay + "/" + (nMonth+1)+"/"+ nYear);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
        otherButton =findViewById(R.id.btnother);
        otherTextView =findViewById(R.id.textother);

        listItems = getResources().getStringArray(R.array.locations_array);
        checkedItems = new boolean[listItems.length];
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Offer_transport.this);
                mBuilder.setTitle("Intermediate cities");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! userItems.contains(position)){
                                userItems.add(position);
                            }
                        } else if(!userItems.contains(position)){
                            userItems.remove(position);
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i < userItems.size(); i++){
                            item = item + listItems[userItems.get(i)];
                            cities.add(listItems[userItems.get(i)]);
                            if(i != userItems.size()-1){
                                item= item + ", ";
                            }
                        }
                        otherTextView.setText(item);
                    }
                });
                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i < checkedItems.length;i++){
                            checkedItems[i] = false;
                            userItems.clear();
                            cities.clear();
                            otherTextView.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateButton.getText().equals("Pick a date")){
                    Toast.makeText(getApplicationContext() , "PLEASE CHOSE A DATE", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseReference = FirebaseDatabase.getInstance().getReference("trips");
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String username = sharedPreferences.getString("username","");
                String id= databaseReference.push().getKey();
                String from=spinnerfrom.getSelectedItem().toString();
                String where=spinnerwhere.getSelectedItem().toString();
                String when= dateButton.getText().toString();
                String freests= freeSeats.getText().toString();
                String phonenum= phoneNumTextView.getText().toString();
                Fire_Trip trip= new Fire_Trip(id,username,from,where,when,freests, cities,phonenum);
                databaseReference.child(id).setValue(trip);
                Toast.makeText(getApplicationContext() , "TRIP IS ADDED!", Toast.LENGTH_SHORT).show();

            }
        });
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateButton.getText().equals("Pick a date")){
                    Toast.makeText(getApplicationContext() , "PLEASE CHOSE A DATE", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseReference = FirebaseDatabase.getInstance().getReference("trips");
                Intent intent = getIntent();
                String id=intent.getStringExtra("id");
                String username=intent.getStringExtra("username");
                String from=spinnerfrom.getSelectedItem().toString();
                String where=spinnerwhere.getSelectedItem().toString();
                String when= dateButton.getText().toString();
                String freests= freeSeats.getText().toString();
                String phonenum= phoneNumTextView.getText().toString();
                Fire_Trip trip= new Fire_Trip(id,username,from,where,when,freests, cities,phonenum);
                databaseReference.child(id).setValue(trip);
                Toast.makeText(getApplicationContext() , "TRIP IS MODIFIED!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }
}
