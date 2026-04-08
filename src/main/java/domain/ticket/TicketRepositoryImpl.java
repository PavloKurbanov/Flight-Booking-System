package domain.ticket;

import infrastructure.util.ConnectionManager;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public void save(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Квиток не може бути null!");
        }
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Помилка збереження квитка!", e);
        }
    }

    @Override
    public Ticket findById(Long aLong) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.find(Ticket.class, aLong);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getAll() {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            return entityManager.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Помикла отримання всіх квитків", e);
        }
    }

    @Override
    public void deleteById(Long ticketId) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            entityManager.getTransaction().begin();

            Ticket ticket = entityManager.find(Ticket.class, ticketId);

            if (ticket == null) {
                throw new IllegalArgumentException("Квиток з таким ID не знайдено!");
            }
            entityManager.remove(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Помилка при видаленні квитка", e);
        }
    }

    @Override
    public List<Ticket> findAllByPassengerId(Long passengerId) {
        try (EntityManager em = ConnectionManager.getEntityManager()) {
            return em.createQuery(
                            "SELECT t FROM Ticket t WHERE t.passenger.id = :pid", Ticket.class)
                    .setParameter("pid", passengerId)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Помилка отримання квитків пасажира ID: " + passengerId, e);
        }
    }

    @Override
    public List<Ticket> findAllFlightsId(Long flightId) {
        try (EntityManager em = ConnectionManager.getEntityManager()) {
            return em.createQuery(
                            "SELECT t FROM Ticket t WHERE t.flight.id = :fid", Ticket.class)
                    .setParameter("fid", flightId)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Помилка отримання квитків рейсу ID: " + flightId, e);
        }
    }
}
