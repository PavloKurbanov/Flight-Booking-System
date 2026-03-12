package repository;

import entity.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    void deleteById(Long ticketId);
}
