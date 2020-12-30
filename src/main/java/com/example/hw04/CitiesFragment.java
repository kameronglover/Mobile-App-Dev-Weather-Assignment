package com.example.hw04;
/**
 * Kameron Glover and Maya Hamilton
 * HW 04
 * CitiesFragment.java
 */

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

public class CitiesFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayAdapter<Object> adapter;
    final String TAG = "demo";
    ArrayList<String> names = new ArrayList<>();

    public CitiesFragment() {
        // Required empty public constructor
    }

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View citiesView =  inflater.inflate(R.layout.fragment_cities, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Cities");
        listView = citiesView.findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
        ArrayList<String> keyList = new ArrayList<>(Data.cities.keySet());

        for (int i = 0;i < keyList.size(); i++) {
            for (int j = 0; j < Objects.requireNonNull(Data.cities.get(keyList.get(i))).size(); j ++) {
            names.add(Objects.requireNonNull(Data.cities.get(keyList.get(i))).toArray()[j] + "," + keyList.get(i));
            }
        }

        adapter =  new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.city_layout,R.id.city, names.toArray());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return citiesView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickedCity = names.get(position);
        Log.d(TAG,clickedCity);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.layout, new CurrentWeatherFragment(clickedCity)).addToBackStack(null).commit();
    }
}