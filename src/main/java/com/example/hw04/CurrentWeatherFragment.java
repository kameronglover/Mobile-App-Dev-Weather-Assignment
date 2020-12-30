package com.example.hw04;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Kameron Glover and Maya Hamilton
 * HW 04
 * CurrentWeatherFragment.java
 */

public class CurrentWeatherFragment extends Fragment implements View.OnClickListener {
    final String TAG = "demo";
    private final OkHttpClient client = new OkHttpClient();
    String cty;
    String url = "https://api.openweathermap.org/data/2.5/find?q=";
    String apiKey = "&appid=1a92d2ef31e805d7bf996966ff15f1cd&units=imperial";
    TextView name;
    TextView temp;
    TextView max;
    TextView min;
    TextView desc;
    TextView humid;
    TextView speed;
    TextView degree;
    TextView cloud;
    ImageView img;
    Response res;
    Weather w;
    String path;


    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public CurrentWeatherFragment(String city) {
        this.cty = city;
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
        final View weatherView = inflater.inflate(R.layout.fragment_current_weather, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Current Weather");
        name = weatherView.findViewById(R.id.currCityNm);
        temp = weatherView.findViewById(R.id.currTemp);
        max = weatherView.findViewById(R.id.currTempMax);
        min = weatherView.findViewById(R.id.currTempMin);
        desc = weatherView.findViewById(R.id.CurrDesc);
        humid = weatherView.findViewById(R.id.humid);
        speed = weatherView.findViewById(R.id.windSpeed);
        degree = weatherView.findViewById(R.id.WindDeg);
        cloud = weatherView.findViewById(R.id.cloud);
        img = weatherView.findViewById(R.id.currImg);
        weatherView.findViewById(R.id.button).setOnClickListener(this);
        name.setText(cty);

        Log.d(TAG, url + cty + apiKey);

        Request request = new Request.Builder()
                .url(url + cty + apiKey)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "fail: " + e);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    res = response;
                    ((AppCompatActivity) weatherView.getContext()).runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(Objects.requireNonNull(res.body()).string());
                                final JSONArray jsonList = json.getJSONArray("list");
                                final JSONObject jsonMain = jsonList.getJSONObject(0).getJSONObject("main");
                                final JSONArray jsonWeather = jsonList.getJSONObject(0).getJSONArray("weather");
                                final JSONObject jsonWind = jsonList.getJSONObject(0).getJSONObject("wind");
                                final JSONObject jsonCloud = jsonList.getJSONObject(0).getJSONObject("clouds");

                                w = new Weather(cty,jsonMain.getString("temp"),jsonMain.getString("temp_max"),
                                        jsonMain.getString("temp_min"),jsonWeather.getJSONObject(0).getString("description"),
                                        jsonMain.getString("humidity"), jsonWind.getString("speed"), jsonWind.getString("deg"),
                                        jsonCloud.getString("all"), jsonWeather.getJSONObject(0).getString("icon"), null);

                                Log.d(TAG,jsonWeather.getJSONObject(0).getString("icon"));

                                Picasso.get()
                                        .load("https://openweathermap.org/img/wn/" + w.icon  + "@2x.png")
                                        .resize(50, 50)
                                        .centerCrop()
                                        .into(img);
                                temp.setText(w.temp + " F");
                                max.setText(w.max + " F");
                                min.setText(w.min + " F");
                                desc.setText(w.desc);
                                humid.setText(w.humid + "%");
                                speed.setText(w.speed + " miles/hr");
                                degree.setText(w.degree + " degrees");
                                cloud.setText(w.cloud + "%");

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    ResponseBody rBody = response.body();
                    assert rBody != null;
                    String body = rBody.string();
                    Log.d(TAG, body);
                }
            }
        });
        return weatherView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ForecastFragment(w.name)).addToBackStack(null).commit();
    }
}
