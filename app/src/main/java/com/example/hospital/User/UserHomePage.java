package com.example.hospital.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hospital.R;

public class UserHomePage extends AppCompatActivity {


    ImageView imageView_doctor_list, imageView_clinic_list, imageView_book_app, imageView_view_app, imageView_list_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);


        imageView_doctor_list = findViewById(R.id.imageView_doctor_list);
        imageView_clinic_list = findViewById(R.id.imageView_clinic_list);
        imageView_book_app = findViewById(R.id.imageView_book_app);
        imageView_view_app = findViewById(R.id.imageView_view_app);
        imageView_list_member = findViewById(R.id.imageView_list_member);


        imageView_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoctorList.class));
            }
        });

        imageView_clinic_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ClinicList.class));

            }
        });

        imageView_book_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BookAppointment.class));

            }
        });

        imageView_view_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewAppointment.class));

            }
        });

        imageView_list_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FamilyMember.class));

            }
        });

    }
}