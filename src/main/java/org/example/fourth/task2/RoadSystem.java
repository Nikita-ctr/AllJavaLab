package org.example.fourth.task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Используем коллекцию TreeMap
public class RoadSystem {
    private final Map<String, List<Road>> roadMap;

    public RoadSystem(String filePath) {
        roadMap = new TreeMap<>();
        readRoadsFromFile(filePath);
    }
    private void readRoadsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String city1 = parts[0];
                    String city2 = parts[1];
                    int length = Integer.parseInt(parts[2]);

                    addRoad(city1, city2, length);
                    addRoad(city2, city1, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addRoad(String city1, String city2, int length) {
        if (!roadMap.containsKey(city1)) {
            roadMap.put(city1, new ArrayList<>());
        }
        roadMap.get(city1).add(new Road(city1, city2, length));
    }

    public List<String> findShortestPath(String destination) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prevCities = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (String city : roadMap.keySet()) {
            dist.put(city, Integer.MAX_VALUE);
            prevCities.put(city, null);
        }
        dist.put("Брест", 0);
        queue.add("Брест");
        while (!queue.isEmpty()) {
            String currentCity = queue.poll();
            if (currentCity.equals(destination)) {break;}
            int currentDistance = dist.get(currentCity);
            List<Road> roads = roadMap.get(currentCity);
            if (roads != null) {
                for (Road road : roads) {
                    String nextCity = road.destinationCity();
                    int nextDistance = currentDistance + road.length();

                    if (nextDistance < dist.get(nextCity)) {
                        dist.put(nextCity, nextDistance);
                        prevCities.put(nextCity, currentCity);
                        queue.add(nextCity);
                    }
                }
            }
        }
        List<String> shortestPath = new ArrayList<>();
        String city = destination;
        while (city != null) {
            shortestPath.add(city);
            city = prevCities.get(city);
        }
        Collections.reverse(shortestPath);

        return shortestPath;
    }

    private record Road(String sourceCity, String destinationCity, int length) {}

    public static void main(String[] args) {
        String filePath = "roads.txt";
        RoadSystem roadSystem = new RoadSystem(filePath);
        String dest= "Гомель";
        List<String> shortestPath = roadSystem.findShortestPath(dest);
        System.out.println("Минимальный путь из г. Брест в г. " + dest + ":");
        for (String city : shortestPath) {
            System.out.println(city);
        }
    }
}