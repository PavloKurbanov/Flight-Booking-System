package domain.passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerRepositoryImpl implements PassengerRepository {
    private final Connection connection;

    public PassengerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажир не може бути null!");
        }

        if (passenger.getId() == null) {
            insert(passenger);
        } else {
            update(passenger);
        }
    }

    @Override
    public Passenger findById(Long aLong) {
        String sql = "SELECT * FROM passengers WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, aLong);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    return new Passenger(id, firstName, lastName);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Passenger> getAll() {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                passengers.add(new Passenger(id, firstName, lastName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passengers;
    }

    private void insert(Passenger passenger) {
        String sql = "INSERT INTO passengers (first_name, last_name) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, passenger.getFirstName());
            preparedStatement.setString(2, passenger.getLastName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Збереження пасажира не вдалося, жодного рядка не додано.");
            }

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    passenger.setId(keys.getLong(1));
                }
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(Passenger passenger) {
        String sql = "UPDATE passengers SET first_name = ?, last_name = ? WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, passenger.getFirstName());
            preparedStatement.setString(2, passenger.getLastName());
            preparedStatement.setLong(3, passenger.getId());

            int executed = preparedStatement.executeUpdate();

            if(executed == 0){
                throw new SQLException("Не вдалось оновити дані пасажира");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
