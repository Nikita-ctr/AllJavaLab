package org.example.third.interf;

public class Main {
    public static void main(String[] args) {

        Car car = new Car("ABC123", "Toyota", "Camry", "VIN123456789", "John Doe", "Gasoline",false);
        System.out.println("Car Details:");
        System.out.println("Registration Number: " + car.getRegistrationNumber());
        System.out.println("Brand: " + car.getBrand());
        System.out.println("Model: " + car.getModel());
        System.out.println("VIN Number: " + car.getVINNumber());
        System.out.println("Owner: " + car.getOwner());
        System.out.println("Power Type: " + car.getPowerType());


        car.refuel();
        car.repair();
        car.service();
        car.setNeedsService(true);
        car.passTechnicalInspection();

        System.out.println("-------------------------------------------------");


        Motorcycle motorcycle = new Motorcycle("XYZ789", "Honda", "CBR1000RR", "VIN987654321", "Jane Smith", "Gasoline",false);
        System.out.println("Motorcycle Details:");
        System.out.println("Registration Number: " + motorcycle.getRegistrationNumber());
        System.out.println("Brand: " + motorcycle.getBrand());
        System.out.println("Model: " + motorcycle.getModel());
        System.out.println("VIN Number: " + motorcycle.getVINNumber());
        System.out.println("Owner: " + motorcycle.getOwner());
        System.out.println("Power Type: " + motorcycle.getPowerType());


        motorcycle.refuel();
        motorcycle.repair();
        motorcycle.activateSportMode();
        motorcycle.service();
        motorcycle.passTechnicalInspection();
        motorcycle.activateSportMode();
    }
}