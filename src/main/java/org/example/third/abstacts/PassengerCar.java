package org.example.third.abstacts;

// Класс для пассажирского вагона
class PassengerCar extends RollingStock {
    private final int passengerCount;
    private final double luggageCapacity;
    private final int comfortLevel;

    public PassengerCar(String id, int passengerCount, double luggageCapacity, int comfortLevel) {
        super(id);
        this.passengerCount = passengerCount;
        this.luggageCapacity = luggageCapacity;
        this.comfortLevel = comfortLevel;
    }

    @Override
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
