package com.example.ivan.requiemapp.models;

import com.google.firebase.database.Exclude;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ivan on 3.1.2017..
 */

public class PlacesModel {
    public String name;

    public PlacesModel() {
    }

    public PlacesModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Map<String, PlacesModel> toMap(){
        HashMap<String, PlacesModel> result = new HashMap<>();
        result.put("mjesta", this);

        return result;
    }


}
