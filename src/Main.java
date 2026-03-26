import domain.ticket.*;
import infrastructure.io.InputOutput;
import domain.flight.FlightRepository;
import domain.passenger.PassengerRepository;
import domain.flight.InFileFlightRepository;
import domain.passenger.InFilePassengerRepository;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.flight.FlightServiceImpl;
import domain.passenger.PassengerServiceImpl;
import ui.menu.MainMenu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    Path flight = Paths.get("flight.txt");
    Path ticket = Paths.get("ticket.txt");
    Path passenger = Paths.get("passenger.txt");

    InputOutput inputOutput = new InputOutput();
    FlightRepository flightRepository = new InFileFlightRepository(flight);
    TicketRepository ticketRepository = new InFileTicketRepository(ticket);
    PassengerRepository passengerRepository = new InFilePassengerRepository(passenger);

    FlightService flightService = new FlightServiceImpl(flightRepository);
    PassengerService passengerService = new PassengerServiceImpl(passengerRepository);
    TicketService ticketService = new TicketServiceImpl(flightService, passengerService, ticketRepository);
    TicketMapper ticketMapper = new TicketMapper(flightService, passengerService);

    MainMenu mainMenu = new MainMenu(inputOutput, flightService, passengerService, ticketService, ticketMapper);
    mainMenu.showMenu();
}
