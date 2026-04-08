package domain.ticket;

import domain.flight.Flight;
import domain.passenger.Passenger;
import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    public Ticket() {}

    public Ticket(Flight flight, Passenger passenger) {
        this.flight = flight;
        this.passenger = passenger;
    }

    public Long getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}