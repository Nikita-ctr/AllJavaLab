package org.example.third.interf;

public class Motorcycle implements Vehicle {
    private final String registrationNumber;
    private final String brand;
    private final String model;
    private final String VINNumber;
    private final String owner;
    private final String powerType;
    private boolean sportMode;

    public Motorcycle(String registrationNumber, String brand, String model, String VINNumber, String owner, String powerType, boolean sportMode) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.VINNumber = VINNumber;
        this.owner = owner;
        this.powerType = powerType;
        this.sportMode = sportMode;
    }

    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getVINNumber() {
        return VINNumber;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getPowerType() {
        return powerType;
    }

    @Override
    public void refuel() {
        System.out.println("Motorcycle is being refueled.");
    }

    @Override
    public void repair() {
        System.out.println("Motorcycle is being repaired.");
    }

    @Override
    public void service() {
        System.out.println("Motorcycle is being serviced.");
    }

    @Override
    public void passTechnicalInspection() {
        System.out.println("Motorcycle is undergoing technical inspection.");
    }

    public void activateSportMode() {
        if (!sportMode) {
            System.out.println("Sport mode activated!");
            sportMode = true;
        } else {
            System.out.println("Sport mode is already active.");
        }
    }
}