package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Search_transport extends AppCompatActivity {

    Calendar c;
    DatePickerDialog dpd;
    Spinner spinner,spinner2;
    Button buttonDate,search;
    TextView nTv;
    DatabaseReference db;
    public static final String SHARED_PREFS="sharedPrefs";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_transport);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        buttonDate=findViewById(R.id.button);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(Search_transport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int nYear, int nMonth, int nDay) {
                        buttonDate.setText(nDay + "/" + (nMonth+1)+"/"+ nYear);
                    }
                },day,month,year);
                dpd.show();

            }
        });
        search=(Button)findViewById(R.id.button7);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonDate.getText().equals("PICK A DATE")){
                    Toast.makeText(getApplicationContext() , "PLEASE CHOSE A DATE", Toast.LENGTH_SHORT).show();
                    return;
                }
                String from=spinner.getSelectedItem().toString();
                String where=spinner2.getSelectedItem().toString();
                String when=buttonDate.getText().toString();
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("activityfrom","search");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), ListTravels.class);
                intent.putExtra("from", from);
                intent.putExtra("where",where);
                intent.putExtra("when",when);
                startActivity(intent);
            }
        });
    }
}
