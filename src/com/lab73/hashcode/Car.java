package com.lab73.hashcode;

import java.util.ArrayList;
import java.util.List;

public class Car {
    public List<Interception> route;
    public int timeToNextArrival;
    public Interception nextPoint;

    public Car() {
        route = new ArrayList<Interception>();
    }

    @Override
    public String toString() {
        return "Car{" +
                "route=" + route +
                ", timeToNextArrival=" + timeToNextArrival +
                ", nextPoint=" + nextPoint +
                '}';
    }
}
