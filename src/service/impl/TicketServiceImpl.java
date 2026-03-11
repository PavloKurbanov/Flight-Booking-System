package service.impl;

import entity.Passenger;
import entity.Ticket;
import repository.FlightRepository;
import repository.PassengerRepository;
import repository.TicketRepository;
import service.TicketService;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(FlightRepository flightRepository, PassengerRepository passengerRepository, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket buyTicket(Long flightId, Passenger passenger, Integer price) {
        return null;
    }

    @Override
    public void cancelTicket(Long ticketId) {

    }

    @Override
    public List<Ticket> getTicketsByPassenger(String firstName, String lastName) {
        return List.of();
    }

    @Override
    public List<Ticket> getTicketsByFlight(Long flightId) {
        return List.of();
    }

    @Override
    public Ticket findById(Long ticketId) {
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return List.of();
    }
}
