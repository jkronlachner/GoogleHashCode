package com.lab73.hashcode;

import java.util.List;

public class Street implements Comparable<Street> {
    public String name;
    public int length;
    public int score;
    public int secondsForStreet;

    public Street(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public Street(String name) {
        this.name = name;
    }

    public int calculateScore(List<Car> cars)
    {
        int tempScore = 0;
        for (Car car:
             cars) {
            tempScore += car.route.stream().filter(x -> x.outs.contains(this)).count();
        }
        return tempScore;
    }

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", length=" + length +
                '}';
    }

    @Override
    public int compareTo(Street o) {
        return this.name.compareTo(o.name);
    }
}
