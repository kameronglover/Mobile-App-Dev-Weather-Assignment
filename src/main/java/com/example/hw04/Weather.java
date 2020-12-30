package com.example.hw04;

import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
/**
 * Kameron Glover and Maya Hamilton
 * hw 04
 * Weather.java
 */
public class Weather {
    String name;
    String temp;
    String max;
    String min;
    String desc;
    String humid;
    String speed;
    String degree;
    String cloud;
    String icon;
    String date;

    public Weather(String nm, String tp, String maxTemp, String minTemp, String des, String hum, String sd, String deg, String cd, String ic, String dt){
        this.name = nm;
        this.temp = tp;
        this.max = maxTemp;
        this.min = minTemp;
        this.desc = des;
        this.humid = hum;
        this.speed = sd;
        this.degree = deg;
        this.cloud = cd;
        this.icon = ic;
        this.date = dt;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", temp='" + temp + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", desc='" + desc + '\'' +
                ", humid='" + humid + '\'' +
                ", speed='" + speed + '\'' +
                ", degree='" + degree + '\'' +
                ", cloud='" + cloud + '\'' +
                ", icon='" + icon + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
