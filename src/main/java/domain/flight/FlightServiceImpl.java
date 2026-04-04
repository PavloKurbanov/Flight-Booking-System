package domain.flight;

import framework.validatorEngine.ValidationEngine;

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
            throw new IllegalArgumentException("Рейс не може бути null!");
        }

        ValidationEngine.validator(flight);

        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Дата не може бути в минулому!");
        }
        List<Flight> flights = getAll();
        boolean isDuplicate = flights
                .stream()
                .anyMatch(existingFlight -> isSameRouteAndTime(existingFlight, flight));

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
    public void reserveSeats(Long flightId, int seatsToBook) {
        if(seatsToBook <= 0){
            throw new IllegalArgumentException("Кількість місць має бути більшою за нуль!");
        }

        Flight flight = findById(flightId);
        if(flight == null){
            throw new IllegalArgumentException("Не має такого ID!");
        }

        if (flight.getAvailableSeats() >= seatsToBook) {
            int seats = flight.getAvailableSeats() - seatsToBook;
            flight.setAvailableSeats(seats);
            flightRepository.save(flight);
            return;
        }
        throw new IllegalArgumentException("Не достатньо вільних місць!");
    }

    @Override
    public void returnSeats(Long flightId, int seatsToBook) {
        Flight flight = findById(flightId);
        if(flight == null){
            throw new IllegalArgumentException("Не має такого ID!");
        }

        if (flight.getAvailableSeats() + seatsToBook > flight.getTotalSeats()) {
            throw new IllegalArgumentException("Помилка: вільних місць не може бути більше за місткість літака!");
        }
        flight.setAvailableSeats(flight.getAvailableSeats() + seatsToBook);
        flightRepository.save(flight);
    }

    private boolean isSameRouteAndTime(Flight existingFlight, Flight newFlight) {
        return newFlight.getDepartureCity().equalsIgnoreCase(existingFlight.getDepartureCity())
                && newFlight.getArrivalCity().equalsIgnoreCase(existingFlight.getArrivalCity())
                && newFlight.getDepartureTime().equals(existingFlight.getDepartureTime());
    }
}