package domain.passenger;

import infrastructure.util.ConnectionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class PassengerRepositoryImpl implements PassengerRepository {

    @Override
    public void save(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажир не може бути null!");
        }

        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(passenger);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Помилка збереження пасажира", e);
        }
    }

    @Override
    public Passenger findById(Long aLong) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.find(Passenger.class, aLong);
        } catch (Exception e) {
            throw new RuntimeException("Помилка при пошуку за ID: " + aLong, e);
        }
    }

    @Override
    public List<Passenger> getAll() {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.createQuery("SELECT p FROM Passenger p", Passenger.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Помилка при отриманні всіх пасажирів", e);
        }
    }

    @Override
    public Passenger findByFirstNameAndLastName(String firstName, String lastName) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.createQuery("Select p from Passenger p where p.firstName = :firstName and p.lastName = :lastName", Passenger.class)
                    .setParameter("firstName", firstName).setParameter("lastName", lastName).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Помилка пошуку пасажира " + firstName + " " + lastName, e);
        }
    }

    @Override
    public List<Passenger> findAllByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.createQuery("Select p from Passenger p where p.id in :ids", Passenger.class).setParameter("ids", ids).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Помилка при отриманні рейсів за IDs", e);
        }
    }
}
