package com.example.earthquake_report;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EarthQuakeAdapter extends RecyclerView.Adapter<EarthQuakeAdapter.EarthQuakeViewHolder> {

    private Quakes data;
    Context context;
    private static final String LOCATION_SEPARATOR = " of ";

    public EarthQuakeAdapter(Context context, Quakes data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public EarthQuakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new EarthQuakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthQuakeViewHolder holder, int position) {

        holder.magnitude.setText(String.valueOf(data.getFeatures().get(position).getProperties().getMag()));

        GradientDrawable magnitudeCircle = (GradientDrawable) holder.magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(data.getFeatures().get(position).getProperties().getMag());
        magnitudeCircle.setColor(magnitudeColor);



        //returns the loc---
        String originalLocation = data.getFeatures().get(position).getProperties().getPlace();
        //now we can mauplate location text---
        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = "Near the";
            primaryLocation = originalLocation;
        }
        holder.primaryloc.setText(primaryLocation);
        holder.locationOffSet.setText(locationOffset);

        // this is the best way to manuplate date and time----
        Date dateObj = new Date(data.getFeatures().get(position).getProperties().getTime());

        String formattedDate = formatDate(dateObj);
        holder.date.setText(formattedDate);
        String formattedTime = formatTime(dateObj);
        holder.time.setText(formattedTime);

        /*  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=null;
        try {
            date = formatter.parse(String.valueOf(data.getFeatures().get(position).getProperties().getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        formatter = new SimpleDateFormat("LLL dd, yyyy");
        holder.time.setText(formatter.format(date));
       */


    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }

    private String formatDate(Date dateobject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateobject);
    }

    private String formatTime(Date dateobject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateobject);
    }


    @Override
    public int getItemCount() {
        return data.getFeatures().size();
    }

    public  class EarthQuakeViewHolder extends RecyclerView.ViewHolder {
        TextView magnitude;
        TextView primaryloc;
        TextView locationOffSet;
        TextView date;
        TextView time;

        public EarthQuakeViewHolder(@NonNull View itemView) {
            super(itemView);

            magnitude = itemView.findViewById(R.id.magnitude);
            primaryloc = itemView.findViewById(R.id.primary_location);
            locationOffSet = itemView.findViewById(R.id.location_offset);
            date = itemView.findViewById(R.id.date1);
            time = itemView.findViewById(R.id.time1);
        }
    }
}