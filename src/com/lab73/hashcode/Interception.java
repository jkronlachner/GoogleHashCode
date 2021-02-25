package com.lab73.hashcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Interception {
    private final int magicNumber = 5;
    public int id;
    public List<Street> ins = new ArrayList<Street>();
    public List<Street> outs = new ArrayList<Street>();
    public List<Street> greenStreet;

    public Interception() {
    }

    public Interception(int id) {
        this.id = id;
    }

    public Interception addInsStreet(Street street){
        ins.add(street);
        return this;
    }
    public Interception addOutsStreet(Street street){
        outs.add(street);
        return this;
    }

    public int getTotalSecondsOfIntersection(){
        return ins.size() * magicNumber;
    }


    public int getSecondsForStreet(Street street){
        int totalScore = ins.stream().map(x -> x.score).reduce(0, Integer::sum);
        double percentageOfScore = (double) street.score / totalScore;
        return (int) Math.ceil((double) getTotalSecondsOfIntersection() * percentageOfScore);
    }


    public List<Street> getSortedIntersectionsByScore()
    {
        return ins.stream().sorted(Comparator.comparingInt(value -> value.score)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Interception{" +
                "id=" + id +
                ", ins=" + ins +
                ", outs=" + outs +
                ", greenStreet=" + greenStreet +
                "} \n";
    }
}
