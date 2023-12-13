package org.example.third.interf;

public class Car implements Vehicle {
    private final String registrationNumber;
    private final String brand;
    private final String model;
    private final String VINNumber;
    private final String owner;
    private final String powerType;
    private boolean needsService;

    public Car(String registrationNumber, String brand, String model, String VINNumber, String owner, String powerType, boolean needsService) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.VINNumber = VINNumber;
        this.owner = owner;
        this.powerType = powerType;
        this.needsService = needsService;
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
        System.out.println("Car is being refueled.");
    }

    @Override
    public void repair() {
        System.out.println("Car is being repaired.");
        needsService = false;
    }

    @Override
    public void service() {
        System.out.println("Car is being serviced.");
        needsService = false;
    }

    @Override
    public void passTechnicalInspection() {
        System.out.println("Car is undergoing technical inspection.");
        if (needsService) {
            System.out.println("Car failed technical inspection. Service required.");
        } else {
            System.out.println("Car passed technical inspection.");
        }
    }

    public void setNeedsService(boolean needsService) {
        this.needsService = needsService;
    }
}