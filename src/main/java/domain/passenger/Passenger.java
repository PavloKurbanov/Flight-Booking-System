package domain.passenger;

import framework.validatorEngine.validatorAnnotation.NotBlank;
import framework.validatorEngine.validatorAnnotation.NotNull;

import java.util.Objects;

public class Passenger implements Comparable<Passenger> {
    private Long id;

    @NotNull
    @NotBlank(message = "Введіть коректне ім'я!")
    private final String firstName;

    @NotNull
    @NotBlank(message = "Введіть коректне прізвище!")
    private final String lastName;

    public Passenger(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) && Objects.equals(firstName, passenger.firstName) && Objects.equals(lastName, passenger.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public int compareTo(Passenger o) {
        int lastNameComper = this.lastName.compareTo(o.lastName);
        if (lastNameComper != 0) {
            return lastNameComper;
        }

        return this.firstName.compareTo(o.firstName);
    }
}
