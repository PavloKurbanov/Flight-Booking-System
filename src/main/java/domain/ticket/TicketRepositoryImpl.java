package domain.ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepository {
    private final Connection connection;

    public TicketRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Квиток не може бути null!");
        }
        if (ticket.getId() == null) {
            insert(ticket);
        } else {
            update(ticket);
        }
    }

    @Override
    public Ticket findById(Long aLong) {
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, aLong);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    Long passengerId = resultSet.getLong("passenger_id");
                    Long flightId = resultSet.getLong("flight_id");

                    return new Ticket(id, passengerId, flightId);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT * FROM tickets";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                Long passengerId = resultSet.getLong("passenger_id");
                Long flightId = resultSet.getLong("flight_id");

                tickets.add(new Ticket(id, passengerId, flightId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public void deleteById(Long ticketId) {
        String sql = "DELETE FROM tickets WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, ticketId);

            int executed = preparedStatement.executeUpdate();

            if (executed == 0) {
                throw new SQLException("Не вдалось видалити квиток");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> findAllByPassengerId(Long passengerId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE passenger_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, passengerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long passengerIdResult = resultSet.getLong("passenger_id");
                    Long flightId = resultSet.getLong("flight_id");

                    tickets.add(new Ticket(id, passengerIdResult, flightId));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public List<Ticket> findAllFlightsId(Long flightId) {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT * FROM tickets WHERE flight_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flightId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long passenger = resultSet.getLong("passenger_id");
                    Long flight = resultSet.getLong("flight_id");

                    tickets.add(new Ticket(id, passenger, flight));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    private void insert(Ticket ticket) {
        String sql = "INSERT INTO tickets (passenger_id, flight_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, ticket.getPassengerId());
            preparedStatement.setLong(2, ticket.getFlightId());

            int executed = preparedStatement.executeUpdate();

            if (executed == 0) {
                throw new SQLException("Збереження квитка не вдалося, жодного рядка не додано.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(Ticket ticket) {
        String sql = "UPDATE tickets SET passenger_id = ?, flight_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, ticket.getPassengerId());
            preparedStatement.setLong(2, ticket.getFlightId());
            preparedStatement.setLong(3, ticket.getId());

            int executed = preparedStatement.executeUpdate();

            if (executed == 0) {
                throw new SQLException("Не вдалось оновити дані квитка!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
