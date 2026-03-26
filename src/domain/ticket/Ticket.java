package domain.ticket;

import java.util.Objects;

public class Ticket{
    private Long id;
    private final Long passengerId;
    private final Long flightId;


    public Ticket(Long id, Long passengerId, Long flightId) {
        if (passengerId == null) {
            throw new IllegalArgumentException("Введіть коректне ID пасажира!");
        }
        if (flightId == null) {
            throw new IllegalArgumentException("Введіть коректне ID літака!");
        }
        this.id = id;
        this.passengerId = passengerId;
        this.flightId = flightId;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public Long getFlightId() {
        return flightId;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(passengerId, ticket.passengerId) && Objects.equals(flightId, ticket.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerId, flightId);
    }
}
