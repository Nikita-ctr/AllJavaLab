package org.example.fifth.dao.impl;

import org.example.fifth.config.DatabaseConnection;
import org.example.fifth.dao.RequestDao;
import org.example.fifth.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {

    private final Logger logger = LoggerFactory.getLogger(RequestDaoImpl.class);
    private final Connection connection = DatabaseConnection.getConnection();

    public void createRequest(Passanger passenger, Station destinationStation, LocalDateTime tripDateTime) {
        try {
            String insertRequestQuery = "INSERT INTO requests (passenger_id, route_id, trip_datetime) VALUES (?, ?, ?)";

            PreparedStatement insertRequestStatement = connection.prepareStatement(insertRequestQuery, Statement.RETURN_GENERATED_KEYS);
            insertRequestStatement.setInt(1, passenger.id());
            insertRequestStatement.setInt(2, findRoute(destinationStation).id());
            insertRequestStatement.setTimestamp(3, Timestamp.valueOf(tripDateTime));
            insertRequestStatement.executeUpdate();

            ResultSet generatedKeys = insertRequestStatement.getGeneratedKeys();
            int requestId;
            if (generatedKeys.next()) {
                requestId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to create request, no ID obtained.");
            }

            Train chosenTrain = findSuitableTrain(destinationStation, tripDateTime);

            Request request = findRequestById(requestId);
            Ticket ticket = new Ticket(requestId, request, chosenTrain, calculateTicketPrice(chosenTrain));
            saveTicket(ticket);

            generateInvoice(ticket);

        } catch (SQLException e) {
            logger.error("Failed to create request", e);
        }
    }

    private Route findRoute(Station destinationStation) throws SQLException {
        String findRouteQuery = "SELECT * FROM routes WHERE end_station_id = ?";

        PreparedStatement findRouteStatement = connection.prepareStatement(findRouteQuery);
        findRouteStatement.setInt(1, destinationStation.id());

        ResultSet routeResult = findRouteStatement.executeQuery();

        if (routeResult.next()) {
            int routeId = routeResult.getInt("id");
            Station startStation = findStationById(routeResult.getInt("start_station_id"));
            return new Route(routeId, startStation, destinationStation);
        }

        throw new SQLException("No route found for destination station.");
    }

    public Station findStationById(int stationId) throws SQLException {
        String findStationQuery = "SELECT * FROM stations WHERE id = ?";

        PreparedStatement findStationStatement = connection.prepareStatement(findStationQuery);
        findStationStatement.setInt(1, stationId);

        ResultSet stationResult = findStationStatement.executeQuery();

        if (stationResult.next()) {
            int id = stationResult.getInt("id");
            String name = stationResult.getString("name");
            return new Station(id, name);
        }

        throw new SQLException("No station found with ID: " + stationId);
    }

    private Train findSuitableTrain(Station destinationStation, LocalDateTime tripDateTime) throws SQLException {
        String findTrainQuery = "SELECT * FROM trains WHERE id IN (SELECT train_id FROM tickets WHERE train_id NOT IN (SELECT train_id FROM tickets WHERE request_id IN (SELECT id FROM requests WHERE trip_datetime = ?)) AND request_id IN (SELECT id FROM requests WHERE route_id = ?))";

        PreparedStatement findTrainStatement = connection.prepareStatement(findTrainQuery);
        findTrainStatement.setTimestamp(1, Timestamp.valueOf(tripDateTime));
        findTrainStatement.setInt(2, findRoute(destinationStation).id());

        ResultSet trainResult = findTrainStatement.executeQuery();

        if (trainResult.next()) {
            int id = trainResult.getInt("id");
            String trainNumber = trainResult.getString("train_number");
            return new Train(id, trainNumber);
        }

        throw new SQLException("No suitable train found.");
    }

    private BigDecimal calculateTicketPrice(Train train) throws SQLException {
        String findTicketPriceQuery = "SELECT price FROM tickets WHERE train_id = ?";

        PreparedStatement findPriceStatement = connection.prepareStatement(findTicketPriceQuery);
        findPriceStatement.setInt(1, train.id());

        ResultSet priceResult = findPriceStatement.executeQuery();

        if (priceResult.next()) {
            return priceResult.getBigDecimal("price");
        }

        throw new SQLException("Ticket price not found for train with ID: " + train.id());
    }

    private void saveTicket(Ticket ticket) throws SQLException {
        String insertTicketQuery = "INSERT INTO tickets (request_id, train_id, price) VALUES (?, ?, ?)";

        PreparedStatement insertTicketStatement = connection.prepareStatement(insertTicketQuery);
        insertTicketStatement.setInt(1, ticket.request().id());
        insertTicketStatement.setInt(2, ticket.train().id());
        insertTicketStatement.setBigDecimal(3, ticket.price());
        insertTicketStatement.executeUpdate();
        insertTicketStatement.close();
    }

    private void generateInvoice(Ticket ticket) {
        int ticketId = ticket.id();
        Request request = ticket.request();
        BigDecimal price = ticket.price();
        String passengerName = request.passenger().firstName();
        LocalDateTime tripDateTime = request.tripDateTime();
        System.out.println("Счет на оплату");
        System.out.println("Билет ID: " + ticketId);
        System.out.println("Пассажир: " + passengerName);
        System.out.println("Дата и время поездки: " + tripDateTime);
        System.out.println("Цена: " + price);
        logger.info("Счет на оплату");
        logger.info("Билет ID: {}", ticketId);
        logger.info("Пассажир: {}", passengerName);
        logger.info("Дата и время поездки: {}", tripDateTime);
        logger.info("Цена: {}", price);
    }


    public List<Route> getRoutesByPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        List<Route> routes = new ArrayList<>();

        try {
            String findRoutesQuery = "SELECT * FROM routes WHERE id IN (SELECT route_id FROM requests WHERE trip_datetime BETWEEN ? AND ?)";

            PreparedStatement findRoutesStatement = connection.prepareStatement(findRoutesQuery);
            findRoutesStatement.setTimestamp(1, Timestamp.valueOf(startTime));
            findRoutesStatement.setTimestamp(2, Timestamp.valueOf(endTime));

            ResultSet routeResult = findRoutesStatement.executeQuery();

            while (routeResult.next()) {
                int routeId = routeResult.getInt("id");
                Station startStation = findStationById(routeResult.getInt("start_station_id"));
                Station endStation = findStationById(routeResult.getInt("end_station_id"));
                routes.add(new Route(routeId, startStation, endStation));
            }

        } catch (SQLException e) {
            logger.info( "Error occurred while fetching routes by period", e);
        }

        return routes;
    }

    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();

        try {
            String findAllRoutesQuery = "SELECT * FROM routes";
            Statement findAllRoutesStatement = connection.createStatement();
            ResultSet routeResult = findAllRoutesStatement.executeQuery(findAllRoutesQuery);

            while (routeResult.next()) {
                int routeId = routeResult.getInt("id");
                Station startStation = findStationById(routeResult.getInt("start_station_id"));
                Station endStation = findStationById(routeResult.getInt("end_station_id"));
                routes.add(new Route(routeId, startStation, endStation));
            }

        } catch (SQLException e) {
            logger.info( "Error occurred while fetching all routes", e);
        }

        return routes;
    }

    public List<Ticket> getTicketsByPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        List<Ticket> tickets = new ArrayList<>();

        try {
            String findTicketsQuery = "SELECT * FROM tickets WHERE request_id IN (SELECT id FROM requests WHERE trip_datetime BETWEEN ? AND ?)";

            PreparedStatement findTicketsStatement = connection.prepareStatement(findTicketsQuery);
            findTicketsStatement.setTimestamp(1, Timestamp.valueOf(startTime));
            findTicketsStatement.setTimestamp(2, Timestamp.valueOf(endTime));

            ResultSet ticketResult = findTicketsStatement.executeQuery();

            while (ticketResult.next()) {
                int ticketId = ticketResult.getInt("id");
                int requestId = ticketResult.getInt("request_id");
                Train train = findTrainById(ticketResult.getInt("train_id"));
                BigDecimal price = ticketResult.getBigDecimal("price");

                Request request = findRequestById(requestId);

                tickets.add(new Ticket(ticketId, request, train, price));
            }

        } catch (SQLException e) {
            logger.info( "Error occurred while fetching all routes", e);
        }

        return tickets;
    }

    @Override
    public Station findStationByName(String stationName) {
        try {
            String selectStationQuery = "SELECT * FROM stations WHERE name = ?";
            PreparedStatement selectStationStatement = connection.prepareStatement(selectStationQuery);
            selectStationStatement.setString(1, stationName);
            ResultSet resultSet = selectStationStatement.executeQuery();

            if (resultSet.next()) {
                int stationId = resultSet.getInt("id");
                return new Station(stationId, stationName);
            }
        } catch (SQLException e) {
            logger.info( "Error occurred while fetching all routes", e);

        }
        return null;
    }

    private Request findRequestById(int requestId) {
        Request request = null;

        try {
            String findRequestQuery = "SELECT * FROM requests WHERE id = ?";
            PreparedStatement findRequestStatement = connection.prepareStatement(findRequestQuery);
            findRequestStatement.setInt(1, requestId);

            ResultSet requestResult = findRequestStatement.executeQuery();

            if (requestResult.next()) {
                int passengerId = requestResult.getInt("passenger_id");
                int routeId = requestResult.getInt("route_id");
                LocalDateTime tripDatetime = requestResult.getTimestamp("trip_datetime").toLocalDateTime();

                Passanger passenger = findPassengerById(passengerId);
                Route route = findRouteById(routeId);

                request = new Request(requestId, passenger, route, tripDatetime);
            }
        } catch (SQLException e) {
            logger.info( "Error occurred while fetching all routes", e);

        }

        return request;
    }

    private Passanger findPassengerById(int passengerId) {
        Passanger passenger = null;

        try {
            String findPassengerQuery = "SELECT * FROM passanger WHERE id = ?";
            PreparedStatement findPassengerStatement = connection.prepareStatement(findPassengerQuery);
            findPassengerStatement.setInt(1, passengerId);

            ResultSet passengerResult = findPassengerStatement.executeQuery();

            if (passengerResult.next()) {
                String firstName = passengerResult.getString("first_name");
                String lastName = passengerResult.getString("last_name");
                String contactNumber = passengerResult.getString("contact_number");
                String email = passengerResult.getString("email");
                String password = passengerResult.getString("password");
                boolean isLogin = passengerResult.getBoolean("isLogin");
                boolean isAdmin = passengerResult.getBoolean("is_admin");

                passenger = new Passanger(passengerId, firstName, lastName, contactNumber, email, password, isLogin, isAdmin);
            }
        } catch (SQLException e) {
            logger.info( "Error occurred while fetching all routes", e);

        }

        return passenger;
    }

    private Route findRouteById(int routeId) {
        Route route = null;

        try {
            String findRouteQuery = "SELECT * FROM routes WHERE id = ?";
            PreparedStatement findRouteStatement = connection.prepareStatement(findRouteQuery);
            findRouteStatement.setInt(1, routeId);

            ResultSet routeResult = findRouteStatement.executeQuery();

            if (routeResult.next()) {
                int startStationId = routeResult.getInt("start_station_id");
                int endStationId = routeResult.getInt("end_station_id");

                Station startStation = findStationById(startStationId);
                Station endStation = findStationById(endStationId);

                route = new Route(routeId, startStation, endStation);
            }
        } catch (SQLException e) {
            logger.info( "Error occurred while fetching  ", e);
        }

        return route;
    }

    private Train findTrainById(int trainId) throws SQLException {
        String findTrainQuery = "SELECT * FROM trains WHERE id = ?";

        PreparedStatement findTrainStatement = connection.prepareStatement(findTrainQuery);
        findTrainStatement.setInt(1, trainId);

        ResultSet trainResult = findTrainStatement.executeQuery();

        if (trainResult.next()) {
            int id = trainResult.getInt("id");
            String trainNumber = trainResult.getString("train_number");
            return new Train(id, trainNumber);
        }

        throw new SQLException("No train found with ID: " + trainId);
    }

    public void createTrain(String trainNumber) {
        try {
            String insertTrainQuery = "INSERT INTO trains (train_number) VALUES (?)";
            PreparedStatement insertTrainStatement = connection.prepareStatement(insertTrainQuery);
            insertTrainStatement.setString(1, trainNumber);
            insertTrainStatement.executeUpdate();

            System.out.println("Поезд успешно создан.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createRoute(String startStationName, String endStationName) {
        try {
            String selectStartStationQuery = "SELECT id FROM stations WHERE name = ?";
            PreparedStatement selectStartStationStatement = connection.prepareStatement(selectStartStationQuery);
            selectStartStationStatement.setString(1, startStationName);
            ResultSet startStationResult = selectStartStationStatement.executeQuery();
            if (startStationResult.next()) {
                int startStationId = startStationResult.getInt("id");

                String selectEndStationQuery = "SELECT id FROM stations WHERE name = ?";
                PreparedStatement selectEndStationStatement = connection.prepareStatement(selectEndStationQuery);
                selectEndStationStatement.setString(1, endStationName);
                ResultSet endStationResult = selectEndStationStatement.executeQuery();
                if (endStationResult.next()) {
                    int endStationId = endStationResult.getInt("id");

                    String insertRouteQuery = "INSERT INTO routes (start_station_id, end_station_id) VALUES (?, ?)";
                    PreparedStatement insertRouteStatement = connection.prepareStatement(insertRouteQuery);
                    insertRouteStatement.setInt(1, startStationId);
                    insertRouteStatement.setInt(2, endStationId);
                    insertRouteStatement.executeUpdate();

                    System.out.println("Маршрут успешно создан.");
                } else {
                    System.out.println("Станция прибытия не найдена.");
                }
            } else {
                System.out.println("Станция отправления не найдена.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

