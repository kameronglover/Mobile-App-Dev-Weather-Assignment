package com.example.hw04;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Data {
    static public final HashMap<String, ArrayList<String>> cities = new HashMap<String, ArrayList<String>>(){{
        put("US", new ArrayList<String>(Arrays.asList("Charlotte", "Chicago", "New York", "Miami", "San Francisco", "Baltimore", "Houston")));
        put("UK", new ArrayList<String>(Arrays.asList("London", "Bristol", "Cambridge", "Liverpool")));
        put("AE", new ArrayList<String>(Arrays.asList("Abu Dhabi", "Dubai", "Sharjah")));
        put("JP", new ArrayList<String>(Arrays.asList("Tokyo", "Kyoto", "Hashimoto", "Osaka")));
    }};
}
