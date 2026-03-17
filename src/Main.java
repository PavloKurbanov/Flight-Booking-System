import entity.Flight;
import io.InputOutput;
import repository.FlightRepository;
import repository.PassengerRepository;
import repository.TicketRepository;
import repository.impl.InFileFlightRepository;
import repository.impl.InFilePassengerRepository;
import repository.impl.InFileTicketRepository;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import service.impl.FlightServiceImpl;
import service.impl.PassengerServiceImpl;
import service.impl.TicketServiceImpl;
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

    MainMenu mainMenu = new MainMenu(inputOutput, flightService, passengerService, ticketService);
    mainMenu.showMenu();
}
