package com.example.hospital.User;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hospital.R;
import com.example.hospital.Var;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookAppointment extends AppCompatActivity {


    Spinner clinic_spinner, doctor_spinner;
    TextView booking_date, booking_time;
    String date = "", time = "";
    EditText patient_name;
    Button button_booking;

    ArrayList<String> clinic_arrayList = new ArrayList<String>();
    String[] clinic_items;

    ArrayList<String> doctor_arrayList = new ArrayList<String>();
    String[] doctor_items;

    String doctor = "", clinic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        clinic_spinner = findViewById(R.id.clinic_spinner);
        doctor_spinner = findViewById(R.id.doctor_spinner);
        getDoctor();
        getClinic();


        booking_date = findViewById(R.id.booking_date);

        booking_date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        booking_time = findViewById(R.id.booking_time);
        booking_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        patient_name = findViewById(R.id.patient_name);

        button_booking = findViewById(R.id.button_booking);

        button_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patient_name_txt = patient_name.getText().toString().trim();
                boolean flag = true;

                if (patient_name_txt.equals("")) {
                    patient_name.setError("Patient name required");
                    flag = false;
                }
                if (date.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Date ", Toast.LENGTH_LONG).show();

                    flag = false;
                }
                if (time.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Time", Toast.LENGTH_LONG).show();
                    flag = false;
                }
                if (doctor.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Doctor ", Toast.LENGTH_LONG).show();

                    flag = false;
                }
                if (clinic.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Clinic", Toast.LENGTH_LONG).show();
                    flag = false;
                }
                if (!flag) return;


                final String REQUEST_URL = Var.book_appointment;

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest jsonRequest = new StringRequest(
                        Request.Method.POST,
                        REQUEST_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response", response);
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Log.d("response", obj.getString("code"));
                                    if (obj.getString("code").equals("-1")) {
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }
                ) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        int mStatusCode = response.statusCode;
                        Log.d("mStatusCode", String.valueOf(mStatusCode));


                        return super.parseNetworkResponse(response);

                    }

                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();

                        MyData.put("patient_name", patient_name_txt);
                        MyData.put("date", date);
                        MyData.put("time", time);
                        MyData.put("doctor", doctor);
                        MyData.put("clinic", clinic);

                        return MyData;
                    }
                };
                requestQueue.add(jsonRequest);
            }
        });
    }


    private void getDoctor() {

        final String REQUEST_URL = Var.get_doctor_url;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonRequest = new StringRequest(
                Request.Method.POST,
                REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        JSONArray obj = null;
                        try {
                            obj = new JSONArray(response);


                            JSONObject jsondata;
                            for (int i = 0; i < obj.length(); i++) {

                                jsondata = obj.getJSONObject(i);
                                JSONObject data = new JSONObject(jsondata.getString("data"));
                                doctor_arrayList.add(data.getString("name"));

                            }
                            doctor_items = new String[doctor_arrayList.size()];


                            for (int i = 0; i < doctor_arrayList.size(); i++) {
                                doctor_items[i] = doctor_arrayList.get(i);
                            }


                            ArrayAdapter<String> doctor_spinner_adapter = new ArrayAdapter<>(BookAppointment.this, android.R.layout.simple_spinner_dropdown_item, doctor_items);

                            doctor_spinner.setAdapter(doctor_spinner_adapter);
                            doctor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    doctor = doctor_items[position];
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("mStatusCode", String.valueOf(mStatusCode));


                return super.parseNetworkResponse(response);

            }

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();


                return MyData;
            }
        };
        requestQueue.add(jsonRequest);


    }


    private void getClinic() {

        final String REQUEST_URL = Var.get_clinic_url;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonRequest = new StringRequest(
                Request.Method.POST,
                REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        JSONArray obj = null;
                        try {
                            obj = new JSONArray(response);


                            JSONObject jsondata;
                            for (int i = 0; i < obj.length(); i++) {

                                jsondata = obj.getJSONObject(i);
                                JSONObject data = new JSONObject(jsondata.getString("data"));
                                clinic_arrayList.add(data.getString("name"));

                            }
                            clinic_items = new String[clinic_arrayList.size()];


                            for (int i = 0; i < clinic_arrayList.size(); i++) {
                                clinic_items[i] = clinic_arrayList.get(i);
                            }


                            ArrayAdapter<String> clinic_spinner_adapter = new ArrayAdapter<>(BookAppointment.this, android.R.layout.simple_spinner_dropdown_item, clinic_items);

                            clinic_spinner.setAdapter(clinic_spinner_adapter);
                            clinic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    clinic = clinic_items[position];
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("mStatusCode", String.valueOf(mStatusCode));


                return super.parseNetworkResponse(response);

            }

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();


                return MyData;
            }
        };
        requestQueue.add(jsonRequest);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showTimePickerDialog() {
        TimePickerDialog TimePickerDialog = new TimePickerDialog(
                this,
                (timePicker, i, i1) -> onTimeSet(timePicker, i, i1)
                ,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getApplicationContext()));

        TimePickerDialog.show();
    }


    private void showDatePickerDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (datePicker, i, i1, i2) -> onDateSet(datePicker, i, i1, i2),
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


            datePickerDialog.show();
        }
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        time = hourOfDay + ":" + minute;
        booking_time.setText(time);

    }


    private void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = dayOfMonth + "/" + month + "/" + year;
        booking_date.setText(date);
    }


}