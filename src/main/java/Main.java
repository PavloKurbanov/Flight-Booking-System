import domain.flight.*;
import domain.passenger.PassengerRepository;
import domain.passenger.PassengerRepositoryImpl;
import domain.passenger.PassengerService;
import domain.passenger.PassengerServiceImpl;
import domain.ticket.*;
import infrastructure.io.InputOutput;
import ui.menu.MainMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

private final static String URL = "JDBC:mysql://localhost:3306/learning_db";
private final static String USER = "root";
private final static String PASS = "270119Pavlo";

void main() {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
        InputOutput inputOutput = new InputOutput();
        FlightRepository flightRepository = new FlightRepositoryImpl(connection);
        TicketRepository ticketRepository = new TicketRepositoryImpl(connection);
        PassengerRepository passengerRepository = new PassengerRepositoryImpl(connection);

        FlightService flightService = new FlightServiceImpl(flightRepository);
        PassengerService passengerService = new PassengerServiceImpl(passengerRepository);
        TicketService ticketService = new TicketServiceImpl(flightService, passengerService, ticketRepository, connection);
        TicketMapper ticketMapper = new TicketMapper(flightService, passengerService);

        MainMenu mainMenu = new MainMenu(inputOutput, flightService, passengerService, ticketService, ticketMapper);
        mainMenu.showMenu();
    } catch (SQLException e) {
        throw new RuntimeException("Помилка з'єднання з базою", e);
    }
}