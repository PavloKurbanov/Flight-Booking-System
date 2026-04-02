package ui.command;

import domain.flight.Flight;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;

import java.time.LocalDateTime;

@MenuItem(action = 2, description = "Зареєструвати рейс", menuGroup = MenuGroup.REGISTRATION)
public class RegistrationFlightCommand implements Command {
    private final InputOutput inputOutput;
    private final FlightService flightService;

    public RegistrationFlightCommand(InputOutput inputOutput, FlightService flightService) {
        this.inputOutput = inputOutput;
        this.flightService = flightService;
    }

    @Override
    public void command() {
        String departureCity = inputOutput.readString("Введіть місце відправлення: ");
        String arrivalCity = inputOutput.readString("Введіть місце прибуття: ");
        LocalDateTime localDateTime = inputOutput.readDateTime("Введіть час відправлення: ");
        Integer totalSeats = inputOutput.readInt("Введіть кількість місць: ");

        try {
            flightService.save(new Flight(null, departureCity, arrivalCity, localDateTime, totalSeats));
            System.out.println("Рейс " + departureCity + "/" + arrivalCity + " успішно додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА: " + e.getMessage());
        }
    }
}
