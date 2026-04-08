package domain.passenger;

import core.CrudRepository;

import java.util.List;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {
    Passenger findByFirstNameAndLastName(String firstName, String lastName);

    List<Passenger> findAllByIds(List<Long> ids);
}
