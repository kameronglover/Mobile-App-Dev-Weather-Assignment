package com.example.hw04;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import okhttp3.OkHttpClient;

/**
 * Kameron Glover and Maya Hamilton
 * hw 04
 * CitiesAdapter.java
 */
public class CitiesAdapter extends ArrayAdapter<Weather> {
    Weather wObj;

    final String TAG = "demo";

    public CitiesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Weather> objects) {
        super(context, resource, objects);
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_layout, parent, false);
        }

        TextView date = convertView.findViewById(R.id.forDate);
        TextView temp = convertView.findViewById(R.id.forTemp);
        TextView max = convertView.findViewById(R.id.forMax);
        TextView min = convertView.findViewById(R.id.forMin);
        TextView desc = convertView.findViewById(R.id.forDesc);
        TextView humid = convertView.findViewById(R.id.forHum);
        ImageView img = convertView.findViewById(R.id.imageView);
        wObj = getItem(position);
        //Log.d(TAG, "Adapter " + wObj.toString());
        Picasso.get()
                .load("https://openweathermap.org/img/wn/" + wObj.icon + "@2x.png")
                .resize(20, 20)
                .centerCrop()
                .into(img);
        date.setText(wObj.date);
        temp.setText(wObj.temp + " F");
        max.setText("Max "+ wObj.max + " F");
        min.setText("Min " + wObj.min + " F");
        desc.setText(wObj.desc);
        humid.setText("Humidity: " + wObj.humid + "%");
 return convertView;
    }
}
