package com.app.nycschools.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nycschools.Activities.SchoolDetailsActivity;
import com.app.nycschools.Data.SchoolData;
import com.app.nycschools.R;

import java.util.List;


//  20200423-LamarCaaddfiir-NYCSchools
//
//  Created by Lamar Caaddfiir on 4/20/23.
//  Copyright © 2020 Lamar Caaddfiir. All rights reserved.


public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder> {

    private List<SchoolData> schoolDataList;

    public SchoolListAdapter(List<SchoolData> dataList) {
        this.schoolDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.school_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position % 2 == 1)
            holder.cardView.setBackgroundColor(Color.parseColor("#29afda"));
        else
            holder.cardView.setBackgroundColor(Color.parseColor("#2279a6"));
        final SchoolData schoolData = schoolDataList.get(position);
        holder.tv_schoolName.setText(schoolData.getSchoolName());
        holder.tv_address.setText(schoolData.getAddress());
        holder.tv_studentCounts.setText(String.valueOf(schoolData.getTotalStudents()));
        holder.rl_mainItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SchoolDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("dbn", schoolData.getSchoolDbn());
                extras.putString("school", schoolData.getSchoolName());
                intent.putExtras(extras);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schoolDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_schoolName, tv_address, tv_studentCounts;
        public CardView cardView;
        public RelativeLayout rl_mainItem;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_schoolName = itemView.findViewById(R.id.tv_schoolName);
            this.tv_address = itemView.findViewById(R.id.tv_address);
            this.tv_studentCounts = itemView.findViewById(R.id.tv_studentCounts);
            cardView = itemView.findViewById(R.id.card_view);
            rl_mainItem = itemView.findViewById(R.id.rl_mainItem);
        }
    }
}
