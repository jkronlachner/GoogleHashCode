package com.lab73.hashcode;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String file = Reader.readFile("a.txt");
        System.out.println(file);
        parseParameters(file);
    }

    public static void parseParameters(String inputFile) {
        String firstRow = inputFile.split("\n")[0];
        String[] parameters = firstRow.split(" ");
        int durationOfSimulation = Integer.parseInt(parameters[0]);
        int numberOfIntersections = Integer.parseInt(parameters[1]);
        int numberOfStreets = Integer.parseInt(parameters[2]);
        int numberOfCars = Integer.parseInt(parameters[3]);
        int bonusPoints = Integer.parseInt(parameters[4]);

        List<Interception> intersections = parseIntersections(inputFile, numberOfIntersections);
        System.out.println(intersections);

        List<Car> cars = parseCars(inputFile, numberOfCars, numberOfIntersections, intersections);
        System.out.println(cars);

    }

    private static List<Car> parseCars(String inputFile, int numberOfCars, int numberOfIntersections, List<Interception> intercetions) {
        List<Car> cars = new ArrayList<Car>();
        for (int i = 2 + numberOfIntersections; i < numberOfCars + numberOfIntersections + 2; i++) {
            String carRow = inputFile.split("\n")[i];
            String[] carParams = carRow.split(" ");


            Car car = new Car();
            for (int j = 1; j < carParams.length; j++) {
                String streetName = carParams[j];
                System.out.println(streetName);
                Interception interception = new Interception();
                for (Interception intercection:
                        intercetions
                     ) {
                    if(intercection.ins.stream().anyMatch(x => x.name == streetName)){
                        interception = intercection;
                    }
                }
                car.route.add(interception);
            }
            cars.add(car);
        }
        return cars;
    }

    private static List<Interception> parseIntersections(String inputFile, int numberOfIntersections) {
        List<Interception> intersections = new ArrayList<Interception>();
        for (int i = 1; i < numberOfIntersections + 2; i++) {
            String intersectionRow = inputFile.split("\n")[i];
            String[] intersectionParameters = intersectionRow.split(" ");
            int fromInterception = Integer.parseInt(intersectionParameters[0]);
            int toInterception = Integer.parseInt(intersectionParameters[1]);
            String streetName = intersectionParameters[2];
            int timeToPass = Integer.parseInt(intersectionParameters[3]);

            Street street = new Street(streetName, timeToPass);

            if (!intersections.stream().anyMatch(interception -> interception.id == fromInterception)) {
                intersections.add(new Interception(fromInterception).addOutsStreet(street));
            } else {
                var intersection = intersections.stream().filter(interception -> interception.id == fromInterception).findFirst().get();
                intersection.addOutsStreet(street);
            }
            if (!intersections.stream().anyMatch(intersection -> intersection.id == toInterception)) {
                intersections.add(new Interception(toInterception).addInsStreet(street));
            } else {
                var intersection = intersections.stream().filter(interception -> interception.id == toInterception).findFirst().get();
                intersection.addInsStreet(street);
            }
        }
        return intersections;
    }

    private void StartSim(){
        List<Car> cars;
        List<Interception> interceptions = null;
        List<Street> streets;

        StringBuilder finalString = new StringBuilder();
        finalString.append(interceptions.size());
        finalString.append("\n");
        for (Interception interception:
             interceptions) {
            for (Street in :
                    interception.ins) {
                in.secondsForStreet = interception.getSecondsForStreet(in);
            }

            finalString.append(interception.id);
            finalString.append("\n");
            finalString.append(interception.ins.size());
            finalString.append("\n");


        }


    }
}
