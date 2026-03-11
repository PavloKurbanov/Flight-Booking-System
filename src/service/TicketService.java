package service;

import entity.Passenger;
import entity.Ticket;

import java.util.List;

public interface TicketService {

    /**
     * [Транзакція: Купівля]
     * Головний метод системи. Має звернутися до FlightService (зменшити місця),
     * до PassengerService (зберегти/знайти людину), створити об'єкт Ticket і зберегти його в базу.
     */
    Ticket buyTicket(Long flightId, Passenger passenger, Integer price);

    /**
     * [Транзакція: Скасування]
     * Має знайти квиток за ID, звернутися до FlightService, щоб ПОВЕРНУТИ вільне місце назад у літак,
     * і після цього видалити квиток зі своєї бази.
     */
    void cancelTicket(Long ticketId);

    /**
     * [Пошук клієнта]
     * Знаходить усі квитки, які належать конкретній людині за її ім'ям та прізвищем.
     * Знадобиться для меню "Мої рейси".
     */
    List<Ticket> getTicketsByPassenger(String firstName, String lastName);

    /**
     * [Пошук адміністратора]
     * Знаходить усі квитки на конкретний рейс.
     * Корисно, якщо треба вивести список пасажирів перед вильотом.
     */
    List<Ticket> getTicketsByFlight(Long flightId);

    /**
     * [Базовий CRUD]
     * Знайти чек за його унікальним номером (із захистом від null та неіснуючих ID).
     */
    Ticket findById(Long ticketId);

    /**
     * [Базовий CRUD]
     * Витягнути абсолютно всі продані квитки з бази.
     */
    List<Ticket> getAll();
}