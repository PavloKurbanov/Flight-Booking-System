public class TicketViewService {

    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;
    private final TicketMapper mapper;

    public TicketViewService(
            TicketService ticketService,
            FlightService flightService,
            PassengerService passengerService,
            TicketMapper mapper
    ) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.passengerService = passengerService;
        this.mapper = mapper;
    }

    public List<TicketDTO> getAllTicketsForView() {
        List<Ticket> tickets = ticketService.getAll();

        return tickets.stream()
                .map(ticket -> {
                    Flight flight = flightService.findById(ticket.getFlightId());
                    Passenger passenger = passengerService.findById(ticket.getPassengerId());

                    return mapper.toDTO(ticket, flight, passenger);
                })
                .toList();
    }
}
