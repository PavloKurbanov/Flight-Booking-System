package service.impl;

import entity.Passenger;
import repository.PassengerRepository;
import service.PassengerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger save(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажир не може бути null!");
        }
        List<Passenger> passengersAll = getAll();

        Optional<Passenger> first = passengersAll
                .stream()
                .filter(p -> p.getFirstName().trim().equalsIgnoreCase(passenger.getFirstName())
                        && p.getLastName().trim().equalsIgnoreCase(passenger.getLastName()))
                .findFirst();

        if(first.isPresent()){
            return first.get();
        }
        passengerRepository.save(passenger);
        return passenger;
    }
    /*
    1. getAll(): Бере всіх пасажирів з бази.
    2. filter(): Порівнює ім'я та прізвище, попередньо обрізаючи випадкові пробіли (.trim()) та ігноруючи великі/малі літери.
    3. findFirst(): Зупиняє пошук, як тільки знайде першого ж збігу. Повертає "коробку" Optional.
    4. isPresent(): Патерн Upsert. Якщо клієнт вже є в базі, просто дістаємо його з коробки (.get()) і віддаємо, не створюючи дубліката.
    5. save(): Якщо коробка порожня (це нова людина), зберігаємо в базу.
    */

    @Override
    public Passenger findById(Long passengerId) {
        if (passengerId == null) {
            throw new IllegalArgumentException("ID не може бути null!");
        }
        return passengerRepository.findById(passengerId);
    }

    @Override
    public List<Passenger> getAll() {
        return passengerRepository.getAll();
    }

    @Override
    public List<Passenger> findByFistAndLastName(String firstName, String lastName) {
        return getAll()
                .stream()
                .filter(passenger -> passenger.getFirstName().equalsIgnoreCase(firstName)
                        && passenger.getLastName().equalsIgnoreCase(lastName))
                .sorted()
                .collect(Collectors.toList());
    }
    /*
    1. stream().filter(): Відбирає пасажирів, у яких збігаються і ім'я, і прізвище.
    2. sorted().collect(): Сортує результати і повертає їх списком.
    */
}