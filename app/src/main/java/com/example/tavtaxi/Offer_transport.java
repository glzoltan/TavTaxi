package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Offer_transport extends AppCompatActivity {
    Calendar c;
    DatePickerDialog dpd;
    Spinner spinnerfrom,spinnerwhere;
    Button btndate,btnother,btnadd;
    TextView textother;
    DatabaseReference db;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<String> mCities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_transport);
        spinnerfrom = findViewById(R.id.spinnerfrom);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrom.setAdapter(adapter);

        spinnerwhere = (Spinner)findViewById(R.id.spinnerwhere);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerwhere.setAdapter(adapter2);
        btndate=findViewById(R.id.button);

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
                            else{
                                mUserItems.remove(position);
                            }
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
        btnadd=findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseDatabase.getInstance().getReference("trips");
                String id=db.push().getKey();
                String from=spinnerfrom.getSelectedItem().toString();
                String where=spinnerwhere.getSelectedItem().toString();
                String when=btndate.getText().toString();
                Fire_Trip_Offer trip= new Fire_Trip_Offer(id,from,where,when,mCities);
                db.child(id).setValue(trip);

            }
        });
    }
}
