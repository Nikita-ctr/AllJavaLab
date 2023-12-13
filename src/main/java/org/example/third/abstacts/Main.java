package org.example.third.abstacts;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PassengerTrain train = new PassengerTrain();

        // Создание пассажирских вагонов
        PassengerCar car1 = new PassengerCar("A1", 50, 100.0, 30);
        PassengerCar car2 = new PassengerCar("B1", 40, 80.0, 30);
        PassengerCar car3 = new PassengerCar("C1", 60, 120.0, 30);

        // Создание пассажирских VIP вагонов
        //По умолчанию в vip вагонах уровень комфорта равен 100 (заданно в конструкторе класса)
        PassengerVipCar car4 = new PassengerVipCar("M1", 10, 70.0);
        PassengerVipCar car5 = new PassengerVipCar("M1", 20, 60.0);

        //Добавление вагонов разных типов в поезд
        train.addCar(car1);
        train.addCar(car2);
        train.addCar(car3);
        train.addCar(car4);
        train.addCar(car5);

        // Получение общей численности пассажиров и объема багажного помещения
        int totalPassengerCount = train.getTotalPassengerCount();
        double totalLuggageCapacity = train.getTotalLuggageCapacity();

        System.out.printf("Total Passenger Count: %d%n", totalPassengerCount);
        System.out.printf("Total Luggage Capacity: %.2f%n", totalLuggageCapacity);

        // Сортировка вагонов по уровню комфортности
        train.sortByComfortLevel();

        System.out.println(train);
        // Поиск вагонов, соответствующих заданному диапазону числа пассажиров
        int minPassengerCount = 0;
        int maxPassengerCount = 100;

        List<RollingStock> carsInRange = train.findCarsByPassengerCountRange(minPassengerCount, maxPassengerCount);

        System.out.println("Cars in the specified passenger count range from " + minPassengerCount + " to " + maxPassengerCount);
        for (RollingStock car : carsInRange) {
            System.out.printf("Type of Car: %s, Car ID: %s, Passenger Count: %d, Comfort level: %d%n",
                    car.getClass().getSimpleName(), car.getId(), car.getPassengerCount(), car.getComfortLevel());
        }
    }
}