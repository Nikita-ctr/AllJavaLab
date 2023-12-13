package org.example.third.abstacts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Класс для пассажирского поезда
class PassengerTrain {
    private final List<RollingStock> cars;

    public PassengerTrain() {
        cars = new ArrayList<>();
    }

    public void addCar(RollingStock car) {
        cars.add(car);
    }

    public int getTotalPassengerCount() {
        int totalPassengerCount = 0;
        for (RollingStock car : cars) {
            totalPassengerCount += car.getPassengerCount();
        }
        return totalPassengerCount;
    }

    public double getTotalLuggageCapacity() {
        double totalLuggageCapacity = 0.0;
        for (RollingStock car : cars) {
            totalLuggageCapacity += car.getLuggageCapacity();
        }
        return totalLuggageCapacity;
    }

    public void sortByComfortLevel() {
        cars.sort(Comparator.comparing(RollingStock::getComfortLevel));
    }

    public List<RollingStock> findCarsByPassengerCountRange(int minPassengerCount, int maxPassengerCount) {
        List<RollingStock> result = new ArrayList<>();
        for (RollingStock car : cars) {
            int passengerCount = car.getPassengerCount();
            if (passengerCount >= minPassengerCount && passengerCount <= maxPassengerCount) {
                result.add(car);
            }
        }
        return result;
    }
}
