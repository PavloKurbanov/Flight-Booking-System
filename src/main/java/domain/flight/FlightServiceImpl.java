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
    /*
    1. Перевірки (if): Відкидають порожні об'єкти та рейси з минулого, щоб не навантажувати систему.
    2. getAll(): Витягує всі існуючі рейси з бази.
    3. stream().anyMatch(...): Запускає перевірку кожного існуючого рейсу з новим. Як тільки знаходить хоча б один повний збіг — миттєво зупиняється і повертає true (Коротке замикання).
    4. save(): Якщо дублікатів немає, фізично записує новий рейс у базу.
    */

    @Override
    public Flight findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не може бути null!");
        }
        return flightRepository.findById(id);
    }
    /*
    1. Перевірка: Захищає від передачі порожнього ID.
    2. findById(): Звертається до бази і повертає знайдений рейс (або null, якщо такого немає).
    */

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
    /*
    1. Охоронці: Перевіряють, чи адекватна кількість місць (>0) та чи взагалі існує такий рейс у базі.
    2. Бізнес-логіка (if): Якщо вільних місць вистачає, віднімаємо потрібну кількість і міняємо стан літака.
    3. Транзакція: save() фіксує нову кількість місць у базі даних.
    4. Виняток: Якщо місць менше, ніж просять, транзакція відміняється і кидається помилка.
    */

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