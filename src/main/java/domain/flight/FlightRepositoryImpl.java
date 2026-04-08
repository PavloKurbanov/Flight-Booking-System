package domain.flight;

import infrastructure.util.ConnectionManager;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FlightRepositoryImpl implements FlightRepository {

    @Override
    public void save(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Рейс не може бути null!");
        }

        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(flight);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Помилка збереження рейсу", e);
        }

    }

    @Override
    public Flight findById(Long aLong) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.find(Flight.class, aLong);
        } catch (Exception e) {
            throw new RuntimeException("Помилка при пошуку за ID: " + aLong, e);
        }
    }

    @Override
    public List<Flight> getAll() {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.createQuery("Select f from Flight f", Flight.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public List<Flight> findAllByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.createQuery("Select f from Flight f where f.id in :ids", Flight.class).setParameter("ids", ids).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Помилка при отриманні рейсів за IDs", e);
        }
    }
}
