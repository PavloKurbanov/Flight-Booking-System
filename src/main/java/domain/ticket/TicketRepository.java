package domain.ticket;

import core.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    void deleteById(Long ticketId);
}
