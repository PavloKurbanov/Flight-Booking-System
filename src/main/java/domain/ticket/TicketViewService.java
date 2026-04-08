package domain.ticket;

import domain.flight.Flight;
import domain.flight.FlightService;
import domain.passenger.Passenger;
import domain.passenger.PassengerService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketViewService {

    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    public TicketViewService(
            TicketService ticketService,
            FlightService flightService,
            PassengerService passengerService
    ) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    /**
     * Отримує всі квитки для UI без N+1 проблеми.
     *
     * N+1 проблема: якщо ми для кожного квитка окремо будемо запитувати
     * Flight та Passenger, то для N квитків отримаємо N+1 запитів:
     * 1 запит на всі квитки + N запитів на Flights + N запитів на Passengers.
     * Тут ми робимо batch fetch, щоб обмежити кількість запитів до мінімуму.
     */
    public List<TicketDTO> getAllTicketsForView() {
        // 1️⃣ Отримуємо всі квитки з бази одним запитом
        // Це "1" у N+1: один запит для всіх квитків
        List<Ticket> tickets = ticketService.getAll();

        if (tickets.isEmpty()) {
            // Якщо квитків немає, повертаємо порожній список
            return List.of();
        }

        // 2️⃣ Витягуємо всі унікальні FlightId та PassengerId
        // Використовуємо distinct(), щоб уникнути повторних запитів для однакових IDs
        List<Long> flightIds = tickets.stream()
                .map(Ticket::getFlightId)
                .distinct()
                .toList();

        List<Long> passengerIds = tickets.stream()
                .map(Ticket::getPassengerId)
                .distinct()
                .toList();

        // 3️⃣ Отримуємо всі Flights та Passengers одним запитом
        // Тобто batch fetch: замість N окремих запитів, робимо 1 запит для всіх Flight і 1 для всіх Passenger
        Map<Long, Flight> flightMap = flightService.findAllByIds(flightIds)
                .stream()
                // Перетворюємо список Flight у Map для швидкого доступу по ID
                .collect(Collectors.toMap(Flight::getId, f -> f));

        Map<Long, Passenger> passengerMap = passengerService.findAllByIds(passengerIds)
                .stream()
                // Перетворюємо список Passenger у Map для швидкого доступу по ID
                .collect(Collectors.toMap(Passenger::getId, p -> p));

        // 4️⃣ Формуємо DTO (Data Transfer Object) для UI
        // DTO містить тільки ті дані, які потрібні для відображення, без зайвих деталей
        return tickets.stream()
                .map(ticket -> {
                    // Витягуємо Flight та Passenger з мапи по ID
                    Flight flight = flightMap.get(ticket.getFlightId());
                    Passenger passenger = passengerMap.get(ticket.getPassengerId());

                    // Якщо Passenger не знайдено (null), виводимо "Невідомий пасажир"
                    String fullName = (passenger != null)
                            ? passenger.getFirstName() + " " + passenger.getLastName()
                            : "Невідомий пасажир";

                    // Якщо Flight не знайдено (null), використовуємо "N/A" для міст
                    String departureCity = (flight != null) ? flight.getDepartureCity() : "N/A";
                    String arrivalCity = (flight != null) ? flight.getArrivalCity() : "N/A";

                    // Створюємо DTO для одного квитка
                    return new TicketDTO(
                            ticket.getId(),
                            departureCity,
                            arrivalCity,
                            fullName
                    );
                })
                .toList(); // Перетворюємо Stream<TicketDTO> у List<TicketDTO>
    }
}
