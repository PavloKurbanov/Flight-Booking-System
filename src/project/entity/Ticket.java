package project.entity;

import java.util.Objects;

public class Ticket {
    private Integer id;
    private final Integer passengerId;
    private final Integer flightId;
    private final Integer price;

    public Ticket(Integer id, Integer passengerId, Integer flightId, Integer price) {
        if (passengerId == null) {
            throw new IllegalArgumentException("Введіть коректне ID пасажира!");
        }
        if (flightId == null) {
            throw new IllegalArgumentException("Введіть коректне ID літака!");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Ціна не може бути менше або рівне 0!");
        }
        this.id = id;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(passengerId, ticket.passengerId) && Objects.equals(flightId, ticket.flightId) && Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerId, flightId, price);
    }
}
