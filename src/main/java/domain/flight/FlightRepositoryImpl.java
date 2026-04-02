package domain.flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightRepositoryImpl implements FlightRepository {

    private final Connection connection;

    public FlightRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Рейс не може бути null!");
        }
        if (flight.getId() == null) {
            insert(flight);
        } else {
            update(flight);
        }
    }

    @Override
    public Flight findById(Long aLong) {
        String sql = "SELECT * FROM flights WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, aLong);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String departureCity = resultSet.getString("departure_city");
                    String arrivalCity = resultSet.getString("arrival_city");
                    LocalDateTime departureTime = resultSet.getObject("departure_time", LocalDateTime.class);
                    int totalSeats = resultSet.getInt("total_seats");
                    int availableSeats = resultSet.getInt("available_seats");

                    Flight flight = new Flight(id, departureCity, arrivalCity, departureTime, totalSeats);
                    flight.setAvailableSeats(availableSeats);
                    return flight;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Помилка при пошуку рейсу з ID: " + aLong, e);
        }
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> flights = new ArrayList<>();

        String sql = "SELECT * FROM flights";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String departureCity = resultSet.getString("departure_city");
                String arrivalCity = resultSet.getString("arrival_city");
                LocalDateTime departureTime = resultSet.getObject("departure_time", LocalDateTime.class);
                int totalSeats = resultSet.getInt("total_seats");
                int availableSeats = resultSet.getInt("available_seats");

                Flight flight = new Flight(id, departureCity, arrivalCity, departureTime, totalSeats);
                flight.setAvailableSeats(availableSeats);
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    private void insert(Flight flight) {
        String sql = "INSERT INTO flights (departure_city, arrival_city, departure_time, total_seats, available_seats)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, flight.getDepartureCity());
            preparedStatement.setString(2, flight.getArrivalCity());
            preparedStatement.setObject(3, flight.getDepartureTime());
            preparedStatement.setInt(4, flight.getTotalSeats());
            preparedStatement.setInt(5, flight.getTotalSeats());

            int executed = preparedStatement.executeUpdate();

            if (executed == 0) {
                throw new SQLException("Збереження рейсу не вдалося, жодного рядка не додано.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    flight.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не вдалось зберегти рейс у базу", e);
        }
    }

    private void update(Flight flight) {
        String sql = "UPDATE flights SET departure_city = ?, arrival_city = ?, departure_time = ?, " +
                "total_seats = ?, available_seats = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, flight.getDepartureCity());
            preparedStatement.setString(2, flight.getArrivalCity());
            preparedStatement.setObject(3, flight.getDepartureTime());
            preparedStatement.setInt(4, flight.getTotalSeats());
            preparedStatement.setInt(5, flight.getAvailableSeats());
            preparedStatement.setLong(6, flight.getId());

            int executed = preparedStatement.executeUpdate();

            if (executed == 0) {
                throw new SQLException("Не вдалось оновити рейс");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
