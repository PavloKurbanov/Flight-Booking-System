package domain.ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InFileTicketRepository implements TicketRepository {
    private final Path filePath;
    private final Map<Long, Ticket> ticketMap;
    private Long ticketId = 1L;

    public InFileTicketRepository(Path filePath) {
        this.filePath = filePath;
        this.ticketMap = new ConcurrentHashMap<>();
        try {

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            } else {
                loadFile();
            }

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Зберігаю дані квитків!");
                try {
                    saveFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
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
    public void deleteById(Long ticketId) {
        ticketMap.remove(ticketId);
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
        List<String> list = ticketMap.values().stream().map(ticket -> ticket.getId() + ","
                + ticket.getFlightId() + "," + ticket.getPassengerId()).toList();

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

            Ticket ticket = new Ticket(id, flightId, passengerId);
            ticketMap.put(id, ticket);

            if (id >= this.ticketId) {
                this.ticketId = id + 1;
            }
        }
    }
}
