package com.app.nycschools.Activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.nycschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//  20200423-LamarCaaddfiir-NYCSchools
//
//  Created by Lamar Caaddfiir on 4/20/23.
//  Copyright © 2020 Lamar Caaddfiir. All rights reserved.


public class SchoolDetailsActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;
    public CardView card_view_school_data;
    public LinearLayout ll_no_data;
    public TextView tv_schoolName, tv_satMath, tv_satReading, tv_satWriting;
    public String satMath = "", satReading = "", satWriting = "";
    RequestQueue queue;
    private String school_dbn = "", schoolName = "";
    private String webUrl = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json?dbn=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);

        initViews();
        getSchoolDetails();
    }

    public void initViews() {
        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this, R.style.ProgressDialogStyle);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            school_dbn = bundle.getString("dbn");
            schoolName = bundle.getString("school");
        }
        getSupportActionBar().setTitle(schoolName);
        card_view_school_data = findViewById(R.id.card_view_school_data);
        ll_no_data = findViewById(R.id.ll_no_data);
        tv_schoolName = findViewById(R.id.tv_schoolName);
        tv_satMath = findViewById(R.id.tv_satMath);
        tv_satReading = findViewById(R.id.tv_satReading);
        tv_satWriting = findViewById(R.id.tv_satWriting);
        showProgressDialog();
    }

    //ljc - i can use any other library if needed
    public void getSchoolDetails() {
        JsonArrayRequest postReq = new JsonArrayRequest(Request.Method.GET, webUrl + school_dbn, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                if (jsonObject.has("sat_math_avg_score"))
                                    satMath = jsonObject.getString("sat_math_avg_score");
                                if (jsonObject.has("sat_critical_reading_avg_score"))
                                    satReading = jsonObject.getString("sat_critical_reading_avg_score");
                                if (jsonObject.has("sat_writing_avg_score"))
                                    satWriting = jsonObject.getString("sat_writing_avg_score");
                                tv_satMath.setText(satMath);
                                tv_satReading.setText(satReading);
                                tv_satWriting.setText(satWriting);
                                tv_schoolName.setText(schoolName);
                                dismissProgressDialog();
                            } catch (JSONException e) {
                                dismissProgressDialog();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            card_view_school_data.setVisibility(View.VISIBLE);
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            dismissProgressDialog();
                            card_view_school_data.setVisibility(View.GONE);
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), "Oops! Too slow network...", Toast.LENGTH_LONG).show();
                    }
                });
        postReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postReq);
    }

    //ljc - if i had more time i would customize the Progress Dialog and make it common function throughout the project
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.setCancelable(false);
    }

    public void dismissProgressDialog() {
        try {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
