package service;

import entity.Flight;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public interface FlightService {
    Flight save(Flight flight);

    Flight findById(Long id);

    List<Flight> getAll();

    List<Flight> findFlightsForGroup(String departureCity, String arrivalCity, Integer requiredSeats);

    List<Flight> findByDate(LocalDate date);

    void reserveSeats(Long flightId, int seatsToBook);

    List<Flight> getSortedFlights(Comparator<Flight> comparator);
}
