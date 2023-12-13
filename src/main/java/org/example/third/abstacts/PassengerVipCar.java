package org.example.third.abstacts;

//Класс для пассажирского vip вагона
public class PassengerVipCar extends RollingStock {
    private final int passengerCount;
    private final double luggageCapacity;

    private final int comfortLevel;

    public PassengerVipCar(String id, int passengerCount, double luggageCapacity) {
        super(id);
        this.passengerCount = passengerCount;
        this.luggageCapacity = luggageCapacity;
        this.comfortLevel = 100;
    }

    public int getComfortLevel() {
        return comfortLevel;
    }

    @Override
    public int getPassengerCount() {
        return passengerCount;
    }

    @Override
    public double getLuggageCapacity() {
        return luggageCapacity;
    }
}
