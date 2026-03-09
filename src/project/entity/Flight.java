package project.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight implements Comparable<Flight> {
    private Long id;
    private final String departureCity;
    private final String arrivalCity;
    private final LocalDateTime departureTime;
    private final Integer totalSeats;
    private Integer availableSeats;

    public Flight(Long id, String departureCity, String arrivalCity, LocalDateTime departureTime, Integer totalSeats) {
        if (departureCity == null || departureCity.isBlank()) {
            throw new IllegalArgumentException("Введіть коректне місто відправлення!");
        }
        if (arrivalCity == null || arrivalCity.isBlank()) {
            throw new IllegalArgumentException("Введіть коректне місто прибуття!");
        }
        if (departureTime == null) {
            throw new IllegalArgumentException("Введіть коректний час вильоту!");
        }
        if (totalSeats == null || totalSeats <= 10) {
            throw new IllegalArgumentException("Кількість мість не може бути менше 10!");
        }
        this.id = id;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Flight o) {
        return this.departureTime.compareTo(o.departureTime);
    }

    @Override
    public String toString() {
        return String.format(
                "Flight #%d | %s → %s | Date: %s | Seats: %d/%d",
                id,
                departureCity,
                arrivalCity,
                departureTime,
                availableSeats,
                totalSeats
        );
    }
}
