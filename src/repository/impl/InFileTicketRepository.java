package repository.impl;

import entity.Ticket;
import repository.TicketRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InFileTicketRepository implements TicketRepository {
    private final Path filePath;
    private final Map<Long, Ticket> ticketMap;
    private Long ticketId = 1L;

    public InFileTicketRepository(Path filePath) {
        this.filePath = filePath;
        this.ticketMap = new HashMap<>();
    }

    @Override
    public void save(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Квиток не може бути null!");
        }
        if (ticket.getId() == null) {
            ticket.setId(ticketId++);
        }
        ticketMap.put(ticket.getId(), ticket);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ticket findById(Long aLong) {
        return ticketMap.get(aLong);
    }

    @Override
    public List<Ticket> getAll() {
        return new ArrayList<>(ticketMap.values());
    }

    private void saveFile() throws IOException {
        List<String> list = new ArrayList<>();

        for (Ticket ticket : ticketMap.values()) {
            String line = ticket.getId() + "," + ticket.getFlightId() + "," + ticket.getPassengerId() + "," + ticket.getPrice();
            list.add(line);
        }
        Files.write(filePath, list);
    }

    private void loadFile() throws IOException {
        boolean exist = Files.exists(filePath);
        if (!exist) {
            return;
        }

        List<String> allTicket = Files.readAllLines(filePath);

        for (String s : allTicket) {
            String[] split = s.split(",");

            Long id = Long.parseLong(split[0]);
            Long flightId = Long.parseLong(split[1]);
            Long passengerId = Long.parseLong(split[2]);
            Integer price = Integer.parseInt(split[3]);

            Ticket ticket = new Ticket(id, flightId, passengerId, price);
            ticketMap.put(id, ticket);

            if (id >= this.ticketId) {
                this.ticketId = id + 1;
            }
        }
    }
}
