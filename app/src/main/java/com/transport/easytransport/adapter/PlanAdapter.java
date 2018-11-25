package com.transport.easytransport.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.transport.easytransport.BitmapHelper;
import com.transport.easytransport.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;
import transportapisdk.models.Itinerary;
import transportapisdk.models.Leg;
import transportapisdk.models.Waypoint;


public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    ArrayList<Itinerary> itineraries;
    private int rowLayout;
    private Context mContext;

    public PlanAdapter(ArrayList<Itinerary> list, int rowLayout, Context context) {
        this.itineraries = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Itinerary currentNews = itineraries.get(position);
        for (int i =0;i<currentNews.getLegs().size();i++) {
            // Let's draw Walking Legs and Transit Legs slightly differently. To do this, check
            // the Leg Type, either Walking or Transit.
            Leg leg = currentNews.getLegs().get(i);
            if (leg.getType().equalsIgnoreCase("walking")) {
                ImageView walk = new ImageView(mContext);
                walk.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_directions_walk));
                walk.setMaxWidth(60);
                walk.setMaxHeight(60);
                holder.planitemID.addView(walk);
            } else if (leg.getType().equalsIgnoreCase("transit")) {
                ImageView walk = new ImageView(mContext);
                walk.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_directions_bus));
                walk.setMaxWidth(60);
                walk.setMaxHeight(60);
                holder.planitemID.addView(walk);
            }
        }
    }

    @Override
    public int getItemCount() {
        return itineraries == null ? 0 : itineraries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout planitemID;

        public ViewHolder(View itemView) {

            super(itemView);
            planitemID = itemView.findViewById(R.id.planitemID);
        }
    }
}