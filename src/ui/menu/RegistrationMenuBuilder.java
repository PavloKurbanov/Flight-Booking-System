package ui.menu;

import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.BuyTicketCommand;
import ui.command.Command;
import ui.command.RegistrationFlightCommand;
import ui.command.RegistrationPassengerCommand;

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
