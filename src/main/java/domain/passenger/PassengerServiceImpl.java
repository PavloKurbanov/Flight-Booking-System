package domain.passenger;

import framework.validatorEngine.ValidationEngine;

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

        ValidationEngine.validator(passenger);
        List<Passenger> allPassengers = getAll();
        boolean isDuplicate = allPassengers.stream().anyMatch(p -> isSamePassenger(p, passenger));

        if (isDuplicate) {
            throw new IllegalArgumentException("Такий пасажир вже існує!");
        }
        passengerRepository.save(passenger);
        return passenger;
    }

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
    public Passenger findByFistAndLastName(String firstName, String lastName) {
        return passengerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    private boolean isSamePassenger(Passenger existingPassenger, Passenger passenger) {
        return passenger.getFirstName().equalsIgnoreCase(existingPassenger.getFirstName())
                && passenger.getLastName().equalsIgnoreCase(existingPassenger.getLastName());
    }
}