package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class search_transport extends AppCompatActivity {

    Calendar c;
    DatePickerDialog dpd;
    Spinner spinner,spinner2;
    Button nBtn,search;
    TextView nTv;
    DatabaseReference db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_transport);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        nBtn=(Button)findViewById(R.id.button);

        nBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(search_transport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int nYear, int nMonth, int nDay) {
                        nBtn.setText(nDay + "/" + (nMonth+1)+"/"+ nYear);
                    }
                },day,month,year);
                dpd.show();

            }
        });
        search=(Button)findViewById(R.id.button7);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseDatabase.getInstance().getReference("trips");
                String id=db.push().getKey();
                Spinner spn1=(Spinner)findViewById(R.id.spinner);
                String from=spn1.getSelectedItem().toString();
                Spinner spn2=(Spinner)findViewById(R.id.spinner2);
                String where=spn2.getSelectedItem().toString();
                Button btn2=(Button)findViewById(R.id.button);
                String when=btn2.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ListTravels.class);
                startActivity(intent);
            }
        });
    }
}
