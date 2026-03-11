package service.impl;

import entity.Passenger;
import repository.PassengerRepository;
import service.PassengerService;

import java.util.List;

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

        boolean isDuplicate = passengersAll.stream().anyMatch(existingPassenger -> isSamePassenger(existingPassenger, passenger));
        if (isDuplicate) {
            throw new IllegalArgumentException("Такий пасажир вже існує!");
        }
        passengerRepository.save(passenger);
        return passenger;
    }

    @Override
    public Passenger findById(Long passengerId) {
        if()
        return null;
    }

    @Override
    public List<Passenger> getAll() {
        return List.of();
    }

    @Override
    public Passenger findByFistAndLastName(String firstName, String LastName) {
        return null;
    }

    private boolean isSamePassenger(Passenger existingPassenger, Passenger newPassenger) {
        return newPassenger.getFirstName().equalsIgnoreCase(existingPassenger.getFirstName())
                && newPassenger.getLastName().equalsIgnoreCase(existingPassenger.getLastName());
    }
}
