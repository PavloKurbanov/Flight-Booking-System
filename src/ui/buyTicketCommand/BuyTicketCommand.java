package ui.buyTicketCommand;

import entity.Flight;
import entity.Passenger;
import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import util.FlightPrinter;
import util.PassengerPrinter;

import java.util.List;

public class BuyTicketCommand implements Command {
    private final InputOutput inputOutput;
    private final PassengerService passengerService;
    private final FlightService flightService;
    private final TicketService ticketService;

    public BuyTicketCommand(InputOutput inputOutput, PassengerService passengerService, FlightService flightService, TicketService ticketService) {
        this.inputOutput = inputOutput;
        this.passengerService = passengerService;
        this.flightService = flightService;
        this.ticketService = ticketService;
    }

    @Override
    public String choice() {
        return "3";
    }

    @Override
    public void command() {
        List<Flight> allFlight = flightService.getAll();
        List<Passenger> allPassengers = passengerService.getAll();

        if (allFlight.isEmpty()) {
            System.out.println("На жаль, доступних рейсів немає.");
            return;
        }

        if (allPassengers.isEmpty()) {
            System.out.println("База клієнтів порожня. Спочатку зареєструйте пасажира.");
            return;
        }

        FlightPrinter.printFlights(allFlight);
        Long flightID = inputOutput.readLong("Введіть ID рейсу: ");

        PassengerPrinter.printPassenger(allPassengers);
        Long passengerID = inputOutput.readLong("Введіть ID пасажира: ");
        
        Integer price = inputOutput.readInt("Введіть суму рейсу: ");

        try {
            ticketService.save(flightID, passengerID, price);

            Flight flight = flightService.findById(flightID);
            Passenger passenger = passengerService.findById(passengerID);

            System.out.println("Квиток пасажира " + passenger.getFirstName() + " " + passenger.getLastName() +
                    " на рейс " + flight.getDepartureCity() + " / " + flight.getArrivalCity() + " успішно зареєстрований!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка бронювання: " + e.getMessage());
        }
    }
}
