package com.example.focus;

import com.google.android.gms.maps.model.IndoorBuilding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BuildingList {
    ArrayList<IndoorBuilding> buildings = new ArrayList<>();

    public void add(IndoorBuilding indoorBuilding) {
        buildings.add(indoorBuilding);
    }
}
