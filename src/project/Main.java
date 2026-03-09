import project.entity.Flight;
import project.repository.FlightRepository;
import project.repository.imp.InFileFlightRepository;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    Path file = Paths.get("Flight.txt");
    FlightRepository flightRepository = new InFileFlightRepository(file);
    Flight flight = new Flight(null, "Миколаїв", "Львів", LocalDateTime.of(2026, 3, 12, 2,0, 0), 120);
    Flight flight2 = new Flight(null, "Миколаїв", "Львів", LocalDateTime.of(2026, 3, 12, 2,0, 0), 120);
    Flight flight3 = new Flight(null, "Миколаїв", "Львів", LocalDateTime.of(2026, 3, 12, 2,0, 0), 120);

    flightRepository.save(flight2);
    flightRepository.save(flight);
    flight3.setAvailableSeats(100);
    flightRepository.save(flight3);


    List<Flight> all = flightRepository.getAll();
    for (Flight flight1 : all) {
        System.out.println(flight1);
    }
}
