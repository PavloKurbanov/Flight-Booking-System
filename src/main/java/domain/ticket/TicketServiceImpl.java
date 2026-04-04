package domain.ticket;

import domain.flight.Flight;
import domain.flight.FlightService;
import domain.passenger.Passenger;
import domain.passenger.PassengerService;
import framework.validatorEngine.ValidationEngine;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TicketServiceImpl implements TicketService {
    private final FlightService flightService;
    private final PassengerService passengerService;
    private final TicketRepository ticketRepository;
    private final Connection connection;

    public TicketServiceImpl(FlightService flightService, PassengerService passengerService, TicketRepository ticketRepository,
                             Connection connection) {
        this.flightService = flightService;
        this.passengerService = passengerService;
        this.ticketRepository = ticketRepository;
        this.connection = connection;
    }

    @Override
    public void save(Long flightId, Long passengerId) {
        Flight flight = flightService.findById(flightId);
        Passenger passenger = passengerService.findById(passengerId);

        if (flight == null) {
            throw new IllegalArgumentException("Рейс з ID: " + flightId + " не знайдено!");
        }
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажира з ID: " + passengerId + " не знайдено!");
        }
        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("Рейс ID: " + flightId + " не має вільних місць!");
        }

        Ticket ticket = new Ticket(null, passengerId, flightId);
        ValidationEngine.validator(ticket);

        try {
            // Починаємо транзакцію
            connection.setAutoCommit(false);

            // 1. Зменшуємо доступні місця
            flightService.reserveSeats(flightId, 1);

            // 2. Зберігаємо квиток
            ticketRepository.save(ticket);

            // 3. Фіксуємо транзакцію
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback(); // якщо щось пішло не так, скасовуємо зміни
            } catch (SQLException ex) {
                throw new RuntimeException("Не вдалося скасувати транзакцію", ex);
            }
            throw new RuntimeException("Не вдалося забронювати квиток", e);
        } finally {
            try {
                connection.setAutoCommit(true); // повертаємо авто-коміт
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void cancelTicket(Long ticketId, Long passengerId) {
        Ticket ticket = findById(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Квиток не знайдено!");
        }

        if(!Objects.equals(ticket.getPassengerId(), passengerId)){
            throw new IllegalArgumentException("Цей квиток не належить пасажиру");
        }

        try {
            connection.setAutoCommit(false);

            // повертаємо місце
            flightService.returnSeats(ticket.getFlightId(), 1);

            // видаляємо квиток
            ticketRepository.deleteById(ticketId);

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Не вдалося скасувати квиток", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Ticket> getTicketsByPassenger(String firstName, String lastName) {
        Passenger byFistAndLastName = passengerService.findByFistAndLastName(firstName, lastName);

        if (byFistAndLastName == null) {
            return Collections.emptyList();
        }

        Long passengerId = byFistAndLastName.getId();

        return ticketRepository.findAllByPassengerId(passengerId);
    }

    @Override
    public List<Ticket> getTicketsByFlight(Long flightId) {
        if (flightId == null) {
            throw new IllegalArgumentException("Введіть коректні дані!");
        }
        return ticketRepository.findAllFlightsId(flightId);
    }

    @Override
    public Ticket findById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.getAll();
    }
}
