package com.lab73.hashcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Interception {
    private final int magicNumber = 3;
    public int id;
    public List<com.lab73.hashcode.Street> ins = new ArrayList<com.lab73.hashcode.Street>();
    public List<com.lab73.hashcode.Street> outs = new ArrayList<com.lab73.hashcode.Street>();
    public List<com.lab73.hashcode.Street> greenStreet;

    public Interception() {
    }

    public Interception(int id) {
        this.id = id;
    }

    public Interception addInsStreet(com.lab73.hashcode.Street street){
        ins.add(street);
        return this;
    }
    public Interception addOutsStreet(com.lab73.hashcode.Street street){
        outs.add(street);
        return this;
    }

    public int getTotalSecondsOfIntersection(){
        return ins.size() * magicNumber;
    }

    public void calculateScoresForStreets(List<Car> cars){
        ins.stream().forEach(x -> x.calculateScore(cars));
    }


    public int getSecondsForStreet(com.lab73.hashcode.Street street){
        int totalScore = ins.stream().map(x -> x.score).reduce(0, Integer::sum);
        double percentageOfScore = (double) street.score / totalScore;
        return (int) Math.ceil((double) getTotalSecondsOfIntersection() * percentageOfScore);
    }


    public List<Street> getSortedIntersectionsByScore()
    {
        List<Street> ls =  ins.stream().sorted(Comparator.comparingInt(value -> value.score)).collect(Collectors.toList());
        Collections.reverse(ls);
        return ls;
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
