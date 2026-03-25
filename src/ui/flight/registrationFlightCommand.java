package ui.flight;

import entity.Flight;
import io.InputOutput;
import service.FlightService;
import ui.command.Command;

import java.time.LocalDateTime;

public class registrationFlightCommand implements Command {
    private final InputOutput inputOutput;
    private final FlightService flightService;

    public registrationFlightCommand(InputOutput inputOutput, FlightService flightService) {
        this.inputOutput = inputOutput;
        this.flightService = flightService;
    }

    @Override
    public String choice() {
        return "";
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
