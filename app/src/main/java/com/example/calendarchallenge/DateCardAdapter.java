package com.example.calendarchallenge;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DateCardAdapter  extends RecyclerView.Adapter<DateCardAdapter.DateViewHolder>{

    private List<DateInfo> dateList;

    public DateCardAdapter(List<DateInfo> dateList) {
        this.dateList = dateList;
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    @Override
    public void onBindViewHolder(DateViewHolder dateViewHolder, int i) {
        DateInfo di = dateList.get(i);
        dateViewHolder.vText1.setText(di.text1);
        dateViewHolder.vText2.setText(di.text2);
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);

        return new DateViewHolder(itemView);
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        protected TextView vText1;
        protected TextView vText2;

        public DateViewHolder(View v) {
            super(v);
            vText1 = (TextView) v.findViewById(R.id.cardText1);
            vText2 = (TextView) v.findViewById(R.id.cardText2);
        }
    }
}
