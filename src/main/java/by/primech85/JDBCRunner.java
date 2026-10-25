package by.primech85;

import by.primech85.Dao.StationDao;
import by.primech85.Dao.TicketDao;
import by.primech85.Dao.TripDao;
import by.primech85.Dto.StationFilter;

public class JDBCRunner {
    public static void main(String[] args) {

        var ticketDao = TicketDao.getInstance();
        System.out.println(ticketDao.findByAllTripID(3L));

    }
}
