package org.example.fifth.dao;

import org.example.fifth.domain.Passanger;
import org.example.fifth.domain.Route;
import org.example.fifth.domain.Station;
import org.example.fifth.domain.Ticket;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface RequestDao {

    void createRequest(Passanger passenger, Station destinationStation, LocalDateTime tripDateTime);

    List<Route> getRoutesByPeriod(LocalDateTime startTime, LocalDateTime endTime);

    List<Route> getAllRoutes();

    List<Ticket> getTicketsByPeriod(LocalDateTime startTime, LocalDateTime endTime);

    Station findStationByName(String stationId) throws SQLException;

    void createTrain(String trainNumber);

    void createRoute(String startStationName, String endStationName);
}
