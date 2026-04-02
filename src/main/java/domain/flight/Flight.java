package domain.flight;

import framework.validatorEngine.validatorAnnotation.NotBlank;
import framework.validatorEngine.validatorAnnotation.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight implements Comparable<Flight> {
    private Long id;

    @NotNull
    @NotBlank(message = "Введіть коректне місто відправлення!")
    private final String departureCity;

    @NotNull
    @NotBlank(message = "Введіть коректне місто прибуття!")
    private final String arrivalCity;

    @NotNull
    private final LocalDateTime departureTime;

    @NotNull
    private final Integer totalSeats;
    private Integer availableSeats;

    public Flight(Long id, String departureCity, String arrivalCity, LocalDateTime departureTime, Integer totalSeats) {
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
                "domain.flight.Flight #%d | %s → %s | Date: %s | Seats: %d/%d",
                id,
                departureCity,
                arrivalCity,
                departureTime,
                availableSeats,
                totalSeats
        );
    }
}
