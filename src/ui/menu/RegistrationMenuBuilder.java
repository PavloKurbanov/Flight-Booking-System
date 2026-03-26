package ui.menu;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.buyTicketCommand.BuyTicketCommand;
import ui.command.Command;
import ui.flight.RegistrationFlightCommand;
import ui.passenger.RegistrationPassengerCommand;

import java.util.HashMap;
import java.util.Map;

public record RegistrationMenuBuilder(InputOutput inputOutput, FlightService flightService, TicketService ticketService,
                                      PassengerService passengerService) {

    public Map<String, Command> showMenu() {
        Map<String, Command> menu = new HashMap<>();

        Command registrationPassenger = new RegistrationPassengerCommand(inputOutput, passengerService);
        Command registrationFlight = new RegistrationFlightCommand(inputOutput, flightService);
        Command buyTicket = new BuyTicketCommand(inputOutput, passengerService, flightService, ticketService);

        menu.put(registrationPassenger.choice(), registrationPassenger);
        menu.put(registrationFlight.choice(), registrationFlight);
        menu.put(buyTicket.choice(), buyTicket);

        return menu;
    }
}
