package domain.passenger;

import core.CrudRepository;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {
    Passenger findByFirstNameAndLastName(String firstName, String lastName);
}
