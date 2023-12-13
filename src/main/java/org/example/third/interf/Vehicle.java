package org.example.third.interf;

public interface Vehicle {
    String getRegistrationNumber();
    String getBrand();
    String getModel();
    String getVINNumber();
    String getOwner();
    String getPowerType();
    void refuel();
    void repair();
    void service();
    void passTechnicalInspection();
}