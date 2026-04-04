package domain.passenger;

import java.util.List;

public interface PassengerService {
    Passenger save(Passenger passenger);

    Passenger findById(Long passengerId);

    Passenger findByFistAndLastName(String firstName, String LastName);

    List<Passenger> getAll();
}
