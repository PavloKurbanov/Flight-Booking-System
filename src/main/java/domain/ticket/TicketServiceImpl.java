package domain.ticket;

import domain.flight.Flight;
import domain.flight.FlightService;
import domain.passenger.Passenger;
import domain.passenger.PassengerService;
import framework.validatorEngine.ValidationEngine;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TicketServiceImpl implements TicketService {
    private final FlightService flightService;
    private final PassengerService passengerService;
    private final TicketRepository ticketRepository;


    public TicketServiceImpl(FlightService flightService, PassengerService passengerService, TicketRepository ticketRepository) {
        this.flightService = flightService;
        this.passengerService = passengerService;
        this.ticketRepository = ticketRepository;
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

        Ticket ticket = new Ticket(flight, passenger);
        ValidationEngine.validator(ticket);

        try {

            flightService.reserveSeats(flightId, 1);
            ticketRepository.save(ticket);

        } catch (Exception e) {
            throw new RuntimeException("Не вдалося забронювати квиток", e);
        }
    }

    @Override
    public void cancelTicket(Long ticketId, Long passengerId) {
        Ticket ticket = findById(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Квиток не знайдено!");
        }

        if(!Objects.equals(ticket.getPassenger().getId(), passengerId)){
            throw new IllegalArgumentException("Цей квиток не належить пасажиру");
        }

        try {
            flightService.returnSeats(ticket.getFlight().getId(), 1);

            ticketRepository.deleteById(ticketId);
        } catch (Exception e) {
            throw new RuntimeException("Не вдалося скасувати квиток", e);
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

