package domain.passenger;

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

        return getAll().stream()
                .filter(p -> p.getFirstName().trim().equalsIgnoreCase(passenger.getFirstName())
                        && p.getLastName().trim().equalsIgnoreCase(passenger.getLastName()))
                .findFirst().orElseGet(() -> {
                    passengerRepository.save(passenger);
                    return passenger;
                });

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
    public List<Passenger> findByFistAndLastName(String firstName, String lastName) {
        return getAll()
                .stream()
                .filter(passenger -> passenger.getFirstName().equalsIgnoreCase(firstName)
                        && passenger.getLastName().equalsIgnoreCase(lastName))
                .sorted()
                .toList();
    }
    /*
    1. stream().filter(): Відбирає пасажирів, у яких збігаються і ім'я, і прізвище.
    2. sorted().toList(): Сортує результати і повертає їх списком.
    */
}