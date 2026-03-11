package service;

import entity.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    Flight save(Flight flight);

    Flight findById(Long id);

    List<Flight> getAll();

    List<Flight> findFlightsForGroup(String departureCity, String arrivalCity, Integer availableSeats);

    List<Flight> findByDate(LocalDate date);
}
