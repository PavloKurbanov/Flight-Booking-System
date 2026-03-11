package service.impl;

import entity.Flight;
import repository.FlightRepository;
import service.FlightService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight save(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Квиток не може бути null!");
        }
        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Дата не може бути в минулому!");
        }
        List<Flight> flights = getAll();
        boolean isDuplicate = flights
                .stream()
                .anyMatch(existingFlight ->
                        existingFlight.getDepartureCity().equalsIgnoreCase(flight.getDepartureCity())
                                && existingFlight.getArrivalCity().equalsIgnoreCase(flight.getArrivalCity())
                                && existingFlight.getDepartureTime().equals(flight.getDepartureTime()));

        if (isDuplicate) {
            throw new IllegalArgumentException("Такий рейс вже існує!");
        }

        flightRepository.save(flight);
        return flight;
    }

    @Override
    public Flight findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не може бути null!");
        }
        return flightRepository.findById(id);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.getAll();
    }

    @Override
    public List<Flight> findFlightsForGroup(String departureCity, String arrivalCity, Integer availableSeats) {
        return List.of();
    }

    @Override
    public List<Flight> findByDate(LocalDate date) {
        return List.of();
    }
}
