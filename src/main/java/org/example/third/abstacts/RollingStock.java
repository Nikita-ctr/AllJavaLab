package org.example.third.abstacts;

// Абстрактный класс для подвижного железнодорожного транспорта
abstract class RollingStock {
    private final String id;

    public RollingStock(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    //Метод для получения кол-ва пассажиров
    public abstract int getPassengerCount();

    //Метод для получения объема багажного помещения
    public abstract double getLuggageCapacity();

    //Метод для получения уровня комфорта в вагоне
    public abstract int getComfortLevel();
}
