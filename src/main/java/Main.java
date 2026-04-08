import domain.flight.*;
import domain.passenger.PassengerRepository;
import domain.passenger.PassengerRepositoryImpl;
import domain.passenger.PassengerService;
import domain.passenger.PassengerServiceImpl;
import domain.ticket.*;
import infrastructure.io.InputOutput;
import ui.menu.MainMenu;

void main() {

    InputOutput inputOutput = new InputOutput();
    FlightRepository flightRepository = new FlightRepositoryImpl();
    TicketRepository ticketRepository = new TicketRepositoryImpl();
    PassengerRepository passengerRepository = new PassengerRepositoryImpl();

    FlightService flightService = new FlightServiceImpl(flightRepository);
    PassengerService passengerService = new PassengerServiceImpl(passengerRepository);
    TicketService ticketService = new TicketServiceImpl(flightService, passengerService, ticketRepository);
    TicketViewService ticketViewService = new TicketViewService(ticketService);

    MainMenu mainMenu = new MainMenu(inputOutput, flightService, passengerService, ticketService, ticketViewService);
    mainMenu.showMenu();
}