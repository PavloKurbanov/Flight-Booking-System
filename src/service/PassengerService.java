package service;

import entity.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger save(Passenger passenger);

    Passenger findById(Long passengerId);

    List<Passenger> getAll();

    Passenger findByFistAndLastName(String firstName, String LastName);
}
