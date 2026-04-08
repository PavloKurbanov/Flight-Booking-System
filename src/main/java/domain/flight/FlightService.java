package domain.flight;

import java.util.Arrays;
import java.util.List;

public interface FlightService {
    Flight save(Flight flight);

    Flight findById(Long id);

    List<Flight> getAll();

    void reserveSeats(Long flightId, int seatsToBook);

    void returnSeats(Long flightId, int seatsToBook);

    List<Flight> findAllByIds(List<Long> ids);
}
