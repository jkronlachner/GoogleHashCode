package com.lab73.hashcode;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        // write your code here
        String file = Reader.readFile("c.txt");
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



        List<Car> cars = parseCars(inputFile, numberOfStreets, numberOfCars, intersections);

        StartSim(cars, intersections);

    }

    private static List<com.lab73.hashcode.Car> parseCars(String inputFile, int numberOfStreets, int numberOfCars, List<com.lab73.hashcode.Interception> intercetions) {
        List<com.lab73.hashcode.Car> cars = new ArrayList<com.lab73.hashcode.Car>();
        for (int i = numberOfStreets+1; i < numberOfStreets + numberOfCars+1; i++) {
            String carRow = inputFile.split("\n")[i];
            String[] carParams = carRow.split(" ");


            Car car = new Car();
            for (int j = 1; j < carParams.length; j++) {
                String streetName = carParams[j];
                Interception interception = new Interception();
                for (Interception intercection:
                        intercetions
                     ) {
                    if(intercection.ins.stream().anyMatch(street -> street.name.equals(streetName))){
                        interception = intercection;
                    }
                }
                car.route.add(interception);
            }
            cars.add(car);
        }
        return cars;
    }

    private static List<com.lab73.hashcode.Interception> parseIntersections(String inputFile, int numberOfIntersections) {
        List<com.lab73.hashcode.Interception> intersections = new ArrayList<com.lab73.hashcode.Interception>();
        for (int i = 1; i < numberOfIntersections + 2; i++) {
            String intersectionRow = inputFile.split("\n")[i];
            String[] intersectionParameters = intersectionRow.split(" ");
            int fromInterception = Integer.parseInt(intersectionParameters[0]);
            int toInterception = Integer.parseInt(intersectionParameters[1]);
            String streetName = intersectionParameters[2];
            int timeToPass = Integer.parseInt(intersectionParameters[3]);

            com.lab73.hashcode.Street street = new com.lab73.hashcode.Street(streetName, timeToPass);

            if (!intersections.stream().anyMatch(interception -> interception.id == fromInterception)) {
                intersections.add(new com.lab73.hashcode.Interception(fromInterception).addOutsStreet(street));
            } else {
                var intersection = intersections.stream().filter(interception -> interception.id == fromInterception).findFirst().get();
                intersection.addOutsStreet(street);
            }
            if (!intersections.stream().anyMatch(intersection -> intersection.id == toInterception)) {
                intersections.add(new com.lab73.hashcode.Interception(toInterception).addInsStreet(street));
            } else {
                var intersection = intersections.stream().filter(interception -> interception.id == toInterception).findFirst().get();
                intersection.addInsStreet(street);
            }
        }
        return intersections;
    }

    private static void StartSim(List<Car> cars, List<com.lab73.hashcode.Interception> interceptions){
        StringBuilder finalString = new StringBuilder();
        finalString.append(interceptions.stream().filter(x -> x.ins.size() != 0).count());
        int counter = 0;

        for (Interception interception:
             interceptions) {

            interception.calculateScoresForStreets(cars);

            for (com.lab73.hashcode.Street in :
                    interception.ins) {
                in.secondsForStreet = interception.getSecondsForStreet(in);
            }
            if(interception.ins.size() == 0){
                counter++;
                continue;
            }
            finalString.append("\n");
            finalString.append(interception.id);
            finalString.append("\n");
            finalString.append(interception.ins.size());

            for (Street street:
                    interception.getSortedIntersectionsByScore()) {
                if(street.secondsForStreet == 0){
                    street.secondsForStreet = 1;
                }

                finalString.append("\n");
                finalString.append(street.name);
                finalString.append(" ");
                finalString.append(street.secondsForStreet);
            }
        }

        System.out.println("counter" + counter);
        Reader.writeFile(finalString.toString());

    }
}
