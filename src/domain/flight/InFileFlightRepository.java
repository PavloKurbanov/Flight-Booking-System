package domain.flight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InFileFlightRepository implements FlightRepository {
    private final Path filePath;
    private final Map<Long, Flight> flightMap;
    private Long flightId = 1L;

    public InFileFlightRepository(Path filePath) {
        this.filePath = filePath;
        this.flightMap = new ConcurrentHashMap<>();

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            } else {
                loadFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void save(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Літак не може бути null!");
        }

        if (flight.getId() == null) {
            flight.setId(flightId++);
        }
        flightMap.put(flight.getId(), flight);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Flight findById(Long flightId) {
        return flightMap.get(flightId);
    }

    @Override
    public List<Flight> getAll() {
        return new ArrayList<>(flightMap.values());
    }

    private void saveFile() throws IOException {
        List<String> file = flightMap.values().stream().map(flight -> flight.getId() + "," +
                flight.getDepartureCity() + "," + flight.getArrivalCity() + ","
                + flight.getDepartureTime() + "," + flight.getTotalSeats() + "," + flight.getAvailableSeats()).toList();

        Files.write(filePath, file);
    }

    private void loadFile() throws IOException {
        boolean exist = Files.exists(filePath);
        if (!exist) {
            return;
        }

        List<String> linesFlight = Files.readAllLines(filePath);

        for (String s : linesFlight) {
            String[] split = s.split(",");

            Long id = Long.parseLong(split[0]);
            String departureCity = split[1];
            String arrivalCity = split[2];
            LocalDateTime departureTime = LocalDateTime.parse(split[3]);
            Integer totalSeats = Integer.parseInt(split[4]);
            Integer availableSeats = Integer.parseInt(split[5]);

            Flight newFlight = new Flight(id, departureCity, arrivalCity, departureTime, totalSeats);
            newFlight.setAvailableSeats(availableSeats);
            flightMap.put(id, newFlight);

            if (id >= this.flightId) {
                this.flightId = id + 1;
            }
        }
    }
}
