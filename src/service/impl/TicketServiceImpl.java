package service.impl;

import entity.Flight;
import entity.Passenger;
import entity.Ticket;
import repository.TicketRepository;
import service.FlightService;
import service.PassengerService;
import service.TicketService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public void save(Long flightId, Long passengerId, Integer price) {
        Flight flight = flightService.findById(flightId);
        Passenger passenger = passengerService.findById(passengerId);

        if (flight == null) {
            throw new IllegalArgumentException("Рейс з ID: " + flightId + " не знайдено!");
        }
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажира з ID: " + passengerId + " не знайдено!");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна не може бути менше 0!");
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("Рейс ID: " + flightId + " не має вільних місць!");
        }

        flightService.reserveSeats(flightId, 1);
        ticketRepository.save(new Ticket(0L, passengerId, flightId, price));
    }

    @Override
    public void cancelTicket(Long ticketId) {
        // 1. Знаходимо квиток
        Ticket ticket = findById(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Квиток не знайдено!");
        }

        // 2. Повертаємо 1 місце в літак (через FlightService)
        flightService.returnSeats(ticket.getFlightId(), 1);

        // 3. Видаляємо квиток з нашої бази
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public List<Ticket> getTicketsByPassenger(String firstName, String lastName) {
        List<Passenger> byFistAndLastName = passengerService.findByFistAndLastName(firstName, lastName);

        if (byFistAndLastName.isEmpty()) {
            return Collections.emptyList();
        }

        Long passengerId = byFistAndLastName.getFirst().getId();

        return getAll().stream().filter(ticket -> ticket.getPassengerId().equals(passengerId)).collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getTicketsByFlight(Long flightId) {
        if(flightId == null){
            throw new IllegalArgumentException("Введіть коректні дані!");
        }
        return getAll().stream().filter(ticket -> Objects.equals(ticket.getFlightId(), flightId)).collect(Collectors.toList());
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
