package domain.flight;

import core.CrudRepository;

import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Long> {
    List<Flight> findAllByIds(List<Long> ids);
}
