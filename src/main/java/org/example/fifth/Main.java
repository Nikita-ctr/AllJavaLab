package org.example.fifth;

import org.example.fifth.dao.PassangerDao;
import org.example.fifth.dao.RequestDao;
import org.example.fifth.dao.impl.PassangerDaoImpl;
import org.example.fifth.dao.impl.RequestDaoImpl;
import org.example.fifth.domain.Passanger;
import org.example.fifth.domain.Route;
import org.example.fifth.domain.Station;
import org.example.fifth.domain.Ticket;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PassangerDao clientDao = new PassangerDaoImpl();
        RequestDao requestDao = new RequestDaoImpl();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        Passanger passanger;
        while (isRunning) {
            System.out.println("Выберите действие:");
            System.out.println("1) Вход");
            System.out.println("2) Регистрация");
            System.out.println("3) Завершить выполнение приложения");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Введите почту пользователя:");
                    String email = scanner.nextLine();
                    System.out.println("Введите пароль:");
                    String password = scanner.nextLine();
                    try {
                        passanger = clientDao.login(email, password);
                        System.out.println("Вход выполнен успешно");
                        handleLogin(requestDao, scanner, passanger);
                    } catch (RuntimeException e) {
                        System.err.println("Неверный email или пароль. Попробуйте снова.");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    System.out.println("Введите ваше имя");
                    String firstname = scanner.nextLine();
                    System.out.println("Введите вашу фамилию");
                    String lastname = scanner.nextLine();
                    System.out.println("Введите ваш телефон");
                    String phone = scanner.nextLine();
                    System.out.println("Введите ваш email");
                    String em = scanner.nextLine();
                    System.out.println("Введите ваш пароль");
                    String pass = scanner.nextLine();
                    clientDao.register(firstname, lastname, phone, em, pass);
                    break;
                case 3:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }
        }
        scanner.close();
    }

    private static void handleLogin(RequestDao requestDao, Scanner scanner, Passanger passanger) throws SQLException {
        boolean isLoggedIn = true;

        while (isLoggedIn) {
            System.out.println("Выберите действие: ");

            System.out.println("1) Создать заявку: ");
            System.out.println("2) Посмотреть все маршруты: ");
            System.out.println("3) Посмотреть все маршруты по времени: ");
            System.out.println("4) Посмотреть все билеты по времени: ");
            System.out.println("5) Выйти из аккаунта: ");

            if (passanger.isAdmin()) {
                System.out.println("6) Создать поезд: ");
                System.out.println("7) Задать маршрут: ");
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    System.out.println("Введите название станции: ");
                    String stationName = scanner.nextLine();
                    Station station = requestDao.findStationByName(stationName);

                    System.out.println("Введите дату и время поездки в формате 'yyyy-MM-dd HH:mm':");
                    String dateTimeString = scanner.nextLine();
                    LocalDateTime tripDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    requestDao.createRequest(passanger, station, tripDateTime);
                    break;
                }
                case 2: {
                    List<Route> routes = requestDao.getAllRoutes();
                    for (Route route : routes) {
                        System.out.printf("ID: %d%n", route.id());
                        System.out.printf("Начальная станция: %s%n", route.startStation().name());
                        System.out.printf("Конечная станция: %s%n", route.endStation().name());
                        System.out.println();
                    }
                    break;
                }

                case 3: {
                    System.out.println("Введите начальную дату и время в формате 'yyyy-MM-dd HH:mm':");
                    String startDateTimeString = scanner.nextLine();
                    LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    System.out.println("Введите конечную дату и время в формате 'yyyy-MM-dd HH:mm':");
                    String endDateTimeString = scanner.nextLine();
                    LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    List<Route> routes = requestDao.getRoutesByPeriod(startDateTime, endDateTime);

                    System.out.println("Маршруты:");
                    for (Route route : routes) {
                        System.out.printf("ID: %d%n", route.id());
                        System.out.printf("Начальная станция: %s%n", route.startStation().name());
                        System.out.printf("Конечная станция: %s%n", route.endStation().name());
                        System.out.println();
                    }
                    break;
                }

                case 4: {
                    System.out.println("Введите начальную дату и время в формате 'yyyy-MM-dd HH:mm':");
                    String startDateTimeString = scanner.nextLine();
                    LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    System.out.println("Введите конечную дату и время в формате 'yyyy-MM-dd HH:mm':");
                    String endDateTimeString = scanner.nextLine();
                    LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    List<Ticket> tickets = requestDao.getTicketsByPeriod(startDateTime,endDateTime);

                    System.out.println("Билеты:");
                    for (Ticket ticket : tickets) {
                        System.out.printf("ID: %d%n", ticket.id());
                        System.out.printf("Запрос от: %s%n", ticket.request().passenger().firstName());
                        System.out.printf("Поезд: %s%n", ticket.train().trainNumber());
                        System.out.printf("Цена: %s%n", ticket.price().toString());
                        System.out.println();
                    }
                    break;
                }
                case 5: {
                    isLoggedIn = false;
                    break;
                }
                case 6: {
                    System.out.println("Придумайте название поезда: ");
                    String name = scanner.nextLine();
                    requestDao.createTrain(name);
                    break;
                }
                case 7: {
                    System.out.print("Введите название станции отправления: ");
                    String startStationName = scanner.nextLine();

                    System.out.print("Введите название станции прибытия: ");
                    String endStationName = scanner.nextLine();

                    requestDao.createRoute(startStationName,endStationName);
                    break;
                }
                default: {
                    System.out.println("Некоректный выбор!!!!");
                    break;
                }
            }
        }
    }
}