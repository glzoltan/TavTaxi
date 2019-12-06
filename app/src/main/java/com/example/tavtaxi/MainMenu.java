package com.example.tavtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

public class MainMenu extends AppCompatActivity {
    Button opensearch,openoffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        opensearch =(Button)findViewById(R.id.button2);
        openoffer=(Button)findViewById(R.id.button4);
        opensearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_transport.class);
                startActivity(intent);

            }
        });
        openoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Offer_transport.class);
                startActivity(intent);

            }
        });

    }

}
