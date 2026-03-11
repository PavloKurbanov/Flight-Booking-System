package service.impl;

import entity.Flight;
import repository.FlightRepository;
import service.FlightService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight save(Flight flight) {
        // Перевірка хідних даних на null
        if (flight == null) {
            throw new IllegalArgumentException("Квиток не може бути null!");
        }
        // Перевірка чи дата створення не є в минулому
        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Дата не може бути в минулому!");
        }
        // Перевірка на унікальність
        List<Flight> flights = getAll();
        boolean isDuplicate = flights
                .stream()
                .anyMatch(existingFlight -> isSameRouteAndTime(existingFlight, flight));
        // Якщо такий рейс знайдено, помилка
        if (isDuplicate) {
            throw new IllegalArgumentException("Такий рейс вже існує!");
        }
        // Якщо ввсе добре, зберігаємо та повертаємо об'єкт
        flightRepository.save(flight);
        return flight;
    }

    @Override
    public Flight findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Не має такого ID!");
        }
        return flightRepository.findById(id);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.getAll();
    }

    @Override
    public List<Flight> findFlightsForGroup(String departureCity, String arrivalCity, Integer requiredSeats) {
        return getAll()
                .stream()
                .filter(flight -> flight.getDepartureCity().equalsIgnoreCase(departureCity))
                .filter(flight -> flight.getArrivalCity().equalsIgnoreCase(arrivalCity))
                .filter(flight -> flight.getAvailableSeats() >= requiredSeats)
                .sorted().collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByDate(LocalDate date) {
        if (Objects.isNull(date)) {
            throw new IllegalArgumentException("Двтв не може бути null!");
        }
        return getAll().stream().filter(flight -> flight.getDepartureTime().toLocalDate().equals(date)).sorted().collect(Collectors.toList());
    }

    @Override
    public void reserveSeats(Long flightId, int seatsToBook) {
        if(seatsToBook <= 0){
            throw new IllegalArgumentException("Кількість місць має бути більшою за нуль!");
        }
        Flight flight = findById(flightId);
        if (flight.getAvailableSeats() >= seatsToBook) {
            int seats = flight.getAvailableSeats() - seatsToBook;
            flight.setAvailableSeats(seats);
            flightRepository.save(flight);
            return;
        }
        throw new IllegalArgumentException("Не достатньо вільних місць!");
    }

    private boolean isSameRouteAndTime(Flight existingFlight, Flight newFlight) {
        return newFlight.getDepartureCity().equalsIgnoreCase(existingFlight.getDepartureCity())
                && newFlight.getArrivalCity().equalsIgnoreCase(existingFlight.getArrivalCity())
                && newFlight.getDepartureTime().equals(existingFlight.getDepartureTime());
    }

    @Override
    public List<Flight> getSortedFlights(Comparator<Flight> comparator) {
        if (Objects.isNull(comparator)) {
            throw new IllegalArgumentException("Тип сортування не може бутит null!");
        }
        return getAll().stream().sorted(comparator).collect(Collectors.toList());
    }
}
