package com.example.hospital.User;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class DoctorList extends AppCompatActivity {


    LinearLayout linear_doctor_list;
    ImageView alldoctor, Pulmonary, Dental, Cardiology;
    EditText find_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        linear_doctor_list = findViewById(R.id.linear_doctor_list);

        alldoctor = findViewById(R.id.imageView4);
        Pulmonary = findViewById(R.id.imageView5);
        Dental = findViewById(R.id.imageView6);
        Cardiology = findViewById(R.id.imageView7);

        find_doctor = findViewById(R.id.find_doctor);

        find_doctor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                String name = find_doctor.getText().toString().trim();
                finddoctor(name);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        find_doctor.setKeyboardNavigationCluster(true);
                    }
                    finddoctor(name);

                    return true;
                }

                return false;
            }
        });


        alldoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getalldoctor();
            }
        });
        Pulmonary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                get_doctor_specialty("Pulmonary");
            }
        });
        Dental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                get_doctor_specialty("Dental");
            }
        });
        Cardiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                get_doctor_specialty("Cardiology");
            }
        });


    }

    private void finddoctor(String name) {

        final String REQUEST_URL = Var.find_doctor;

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


                            linear_doctor_list.removeAllViews();

                            for (int i = 0; i < obj.length(); i++) {


                                doctorlist(obj.getJSONObject(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                            try {
                                JSONObject data=new JSONObject(response);
                                doctorlist(data);
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }

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
                MyData.put("name", name);


                return MyData;
            }
        };
        requestQueue.add(jsonRequest);


    }

    private void getalldoctor() {

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


                            linear_doctor_list.removeAllViews();

                            for (int i = 0; i < obj.length(); i++) {


                                doctorlist(obj.getJSONObject(i));
                            }
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

    private void get_doctor_specialty(String specialty) {

        final String REQUEST_URL = Var.get_doctor_specialty;

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


                            linear_doctor_list.removeAllViews();

                            for (int i = 0; i < obj.length(); i++) {


                                doctorlist(obj.getJSONObject(i));
                            }
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
                MyData.put("specialty", specialty);


                return MyData;
            }
        };
        requestQueue.add(jsonRequest);


    }

    private void doctorlist(JSONObject jsonObject) throws JSONException {

        JSONObject data = new JSONObject(jsonObject.getString("data"));
        View view = getLayoutInflater().inflate(R.layout.doctor_list_card, null);


        TextView doctor_name = view.findViewById(R.id.doctor_name);

        TextView doctor_specialty = view.findViewById(R.id.doctor_specialty);
        TextView time = view.findViewById(R.id.time);

        doctor_name.setText(data.getString("name"));
        doctor_specialty.setText(data.getString("specialty"));
        time.setText(data.getString("start_time") + "  " + data.getString("end_time"));

        linear_doctor_list.addView(view);

    }
}