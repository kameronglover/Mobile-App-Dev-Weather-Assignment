package com.example.hw04;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/**
 * Kameron Glover and Maya Hamilton
 * hw 04
 * ForecastFragment.java
 */
public class ForecastFragment extends Fragment {
    String cityName;
    TextView name;
    CitiesAdapter adapter;
    ArrayList<Weather> weatherList = new ArrayList<>();
    String url = "https://api.openweathermap.org/data/2.5/forecast?q=";
    String api = "&appid=1a92d2ef31e805d7bf996966ff15f1cd&units=imperial";
    private final OkHttpClient client = new OkHttpClient();
    final String TAG = "demo";
    Response res;
    ListView listView;

    public ForecastFragment() {
        // Required empty public constructor
    }

    public ForecastFragment(String cty) {
        this.cityName = cty;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View forecastView = inflater.inflate(R.layout.fragment_forecast, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Weather Forecast");
        name = forecastView.findViewById(R.id.forName);
        name.setText(cityName);
        listView = forecastView.findViewById(R.id.forListView);
        new DoJsonWorkAsync().execute();
        return forecastView;
    }

    @SuppressLint("StaticFieldLeak")
    class DoJsonWorkAsync extends AsyncTask<CitiesAdapter, CitiesAdapter, CitiesAdapter> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected CitiesAdapter doInBackground(CitiesAdapter... citiesAdapters) {
            Request request = new Request.Builder()
                    .url(url + cityName + api)
                    .build();

            try {
                res = client.newCall(request).execute();
                JSONObject json = new JSONObject(Objects.requireNonNull(res.body()).string());
                final JSONArray jsonList = json.getJSONArray("list");
                for (int i = 0; i < jsonList.length(); i++) {

                    final JSONObject jsonMain = jsonList.getJSONObject(i).getJSONObject("main");
                    final JSONArray jsonWeather = jsonList.getJSONObject(i).getJSONArray("weather");
                    final JSONObject jsonWind = jsonList.getJSONObject(i).getJSONObject("wind");
                    final JSONObject jsonCloud = jsonList.getJSONObject(i).getJSONObject("clouds");
                    Weather w = new Weather(cityName, jsonMain.getString("temp"), jsonMain.getString("temp_max"),
                            jsonMain.getString("temp_min"), jsonWeather.getJSONObject(0).getString("description"),
                            jsonMain.getString("humidity"), jsonWind.getString("speed"), jsonWind.getString("deg"),
                            jsonCloud.getString("all"), jsonWeather.getJSONObject(0).getString("icon"), jsonList.getJSONObject(0).getString("dt_txt"));
                    weatherList.add(w);
                }
                //Log.d(TAG, "Runnable" + weatherList.toString());
                adapter = new CitiesAdapter(Objects.requireNonNull(getActivity()), R.layout.forecast_layout, weatherList);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return adapter;
        }

        @Override
        protected void onPostExecute(CitiesAdapter citiesAdapter) {
            listView.setAdapter(citiesAdapter);
            citiesAdapter.notifyDataSetChanged();
        }
    }
}