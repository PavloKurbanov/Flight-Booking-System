package domain.passenger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InFilePassengerRepository implements PassengerRepository {
    private final Path filePath;
    private final Map<Long, Passenger> passengerMap;
    private Long passengerID = 1L;

    public InFilePassengerRepository(Path filePath) {
        this.filePath = filePath;
        this.passengerMap = new ConcurrentHashMap<>();
        try {

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            } else {
                loadFile();
            }

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Зберігаю дані пасажира!");
                try {
                    saveFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void save(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажир не може бути null!");
        }
        if (passenger.getId() == null) {
            passenger.setId(passengerID++);
        }
        passengerMap.put(passenger.getId(), passenger);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Passenger findById(Long passengerID) {
        return passengerMap.get(passengerID);
    }

    @Override
    public List<Passenger> getAll() {
        return new ArrayList<>(passengerMap.values());
    }

    private void saveFile() throws IOException {
        List<String> passengers = passengerMap.values().stream().map(passenger -> passenger.getId() + ","
                + passenger.getFirstName() + "," + passenger.getLastName()).toList();

        Files.write(filePath, passengers);
    }

    private void loadFile() throws IOException {
        boolean exist = Files.exists(filePath);
        if (!exist) {
            return;
        }

        List<String> passengers = Files.readAllLines(filePath);

        for (String passenger : passengers) {
            String[] split = passenger.split(",");

            Long id = Long.parseLong(split[0]);
            String firstName = split[1];
            String lastName = split[2];

            Passenger newPassenger = new Passenger(id, firstName, lastName);
            passengerMap.put(id, newPassenger);

            if (id >= this.passengerID) {
                this.passengerID = id + 1;
            }
        }
    }
}
