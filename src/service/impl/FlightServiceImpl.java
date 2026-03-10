package service.impl;

import entity.Flight;
import repository.FlightRepository;
import service.FlightService;

import java.util.List;

public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight save(Flight flight) {
        if()
        return null;
    }

    @Override
    public Flight findById(Long id) {
        return null;
    }

    @Override
    public List<Flight> getAll() {
        return List.of();
    }
}
