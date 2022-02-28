package com.example.hospital.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hospital.R;

public class AdminHomePage extends AppCompatActivity {


    ImageView doctor_add, clinic_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);


        doctor_add = findViewById(R.id.doctor_add);
        clinic_add = findViewById(R.id.clinic_add);

        doctor_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddDoctor.class));
            }
        });

        clinic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddClinic.class));

            }
        });

    }
}