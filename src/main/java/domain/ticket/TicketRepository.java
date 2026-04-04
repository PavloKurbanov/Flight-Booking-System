package domain.ticket;

import core.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    void deleteById(Long ticketId);

    List<Ticket> findAllByPassengerId(Long passengerId);

    List<Ticket> findAllFlightsId(Long flightId);
}