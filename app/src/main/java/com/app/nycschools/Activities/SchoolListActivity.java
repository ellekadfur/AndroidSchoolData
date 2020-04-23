package com.app.nycschools.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.nycschools.Adapters.SchoolListAdapter;
import com.app.nycschools.Data.SchoolData;
import com.app.nycschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


//  20200423-LamarCaaddfiir-NYCSchools
//
//  Created by Lamar Caaddfiir on 4/20/23.
//  Copyright © 2020 Lamar Caaddfiir. All rights reserved.


public class SchoolListActivity extends AppCompatActivity {

    //ljc - I have used here SchoolData model to store the data serially in a object

    public ProgressDialog progressDialog;
    RequestQueue queue;
    CardView card_view;
    RecyclerView rv_school_list;
    SchoolListAdapter schoolListAdapter;
    SchoolData[] schoolData;
    private String webUrl = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    private List<SchoolData> schoolDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        initViews();
        fetchAllSchools();
    }

    private void initViews() {
        //ljc - if i had more time i would use custom toolbar for header
        getSupportActionBar().setTitle("NYC Schools List");
        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this, R.style.ProgressDialogStyle);
        rv_school_list = findViewById(R.id.rv_school_list);
        schoolData = new SchoolData[]{};
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_school_list.setLayoutManager(mLayoutManager);
        rv_school_list.setItemAnimator(new DefaultItemAnimator());
        rv_school_list.setAdapter(schoolListAdapter);
        showProgressDialog();
    }


    private void fetchAllSchools() {
        StringRequest postReq = new StringRequest(Request.Method.GET, webUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray rootJsonArray = new JSONArray(response);
                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                String dbn = "", school_name = "", phone_number = "", website = "", school_email = "", address = "", city = "", zip = "", borough = "";
                                int total_students = 0;
                                JSONObject jsonObject = rootJsonArray.getJSONObject(i);
                                dbn = jsonObject.getString("dbn");
                                school_name = jsonObject.getString("school_name");
                                if (jsonObject.has("phone_number"))
                                    phone_number = jsonObject.getString("phone_number");
                                if (jsonObject.has("website"))
                                    website = jsonObject.getString("website");
                                if (jsonObject.has("school_email"))
                                    school_email = jsonObject.getString("school_email");
                                if (jsonObject.has("total_students"))
                                    total_students = Integer.parseInt(jsonObject.getString("total_students"));
                                if (jsonObject.has("primary_address_line_1"))
                                    address = jsonObject.getString("primary_address_line_1");
                                if (jsonObject.has("city"))
                                    phone_number = jsonObject.getString("phone_number");
                                if (jsonObject.has("zip"))
                                    zip = jsonObject.getString("zip");
                                if (jsonObject.has("borough"))
                                    borough = jsonObject.getString("borough");
                                schoolData = new SchoolData[]{
                                        new SchoolData(dbn, school_name, phone_number, website, school_email, total_students, address, city, zip, borough)
                                };
                                SchoolData schoolData1 = new SchoolData(dbn, school_name, phone_number, website, school_email,
                                        total_students, address, city, zip, borough);
                                schoolDataList.add(schoolData1);
                            }
                            schoolListAdapter = new SchoolListAdapter(schoolDataList);
                            rv_school_list.setAdapter(schoolListAdapter);
                            dismissProgressDialog();
                        } catch (JSONException e) {
                            dismissProgressDialog();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissProgressDialog();
                        //ljc - if i had more time i would use custom Utility for network checking
                        System.out.println(error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        //ljc - if i had more time i would customize the Alert Dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SchoolListActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
        alertDialogBuilder
                .setMessage("Do you want to exit the App?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
