package domain.passenger;

import domain.ticket.Ticket;
import framework.validatorEngine.validatorAnnotation.NotBlank;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "passengers")
public class Passenger implements Comparable<Passenger> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Введіть коректне ім'я!")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Введіть коректне прізвище!")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Passenger() {}

    public Passenger(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Passenger o) {
        if (o == null) return 1;
        int lastNameCompare = this.lastName.compareTo(o.lastName);
        if (lastNameCompare != 0) {
            return lastNameCompare;
        }
        return this.firstName.compareTo(o.firstName);
    }
}