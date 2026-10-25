package by.primech85.service;

import by.primech85.Dao.TicketDao;
import by.primech85.Dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private final static TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();
    private TicketService(){

    }
    public static TicketService getInstance(){
        return INSTANCE;
    }
    public List<TicketDto> findAllTripId(Long trip_id){
        return ticketDao.findByAllTripID(trip_id).stream().map(
                ticket -> new TicketDto(
                        ticket.getId(),
                        ticket.getTrip().getId(),
                        ticket.getSeat_no())
        ).collect(Collectors.toList());
    }
}
