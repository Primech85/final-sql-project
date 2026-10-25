package by.primech85.Dao;

import by.primech85.entity.Ticket;
import by.primech85.entity.Trip;
import by.primech85.utils.ConnectionManager;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao implements Dao<Ticket,Long> {
    private final static String FIND_ALL_SQL = """
            select id,cost,passenger_name,passenger_passport,seat_no,trip_id from ticket
            """;
    private final static String FIND_BY_ID_SQL= FIND_ALL_SQL + """
            where id = ?
            """;
    private final static String FIND_BY_ALL_TRIP_ID_SQL =FIND_ALL_SQL+ """
            WHERE trip_id = ?
            """;





    private final static TicketDao INSTANCE = new TicketDao();
    private TripDao tripDao = TripDao.getInstance();
    private TicketDao(){

    }
    public static TicketDao getInstance(){
        return INSTANCE;
    }

    @Override
    public boolean update(Ticket ticket) {
        return false;
    }

    @Override
    public List<Ticket> findAll() {
        return List.of();
    }

    public List<Ticket> findByAllTripID(Long id){
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_ALL_TRIP_ID_SQL)) {
            statement.setLong(1,id);
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();
            while(result.next()){
                tickets.add(build(result));
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> findById(Long aLong) {
        Ticket ticket = new Ticket();
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1,aLong);
            var result = statement.executeQuery();
            if(result.next()){
                ticket = build(result);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public Ticket build(ResultSet result) throws SQLException {
        return new Ticket(
                result.getLong("id"),
                result.getBigDecimal("cost"),
                result.getString("passenger_name"),
                result.getString("passenger_passport"),
                result.getString("seat_no"),
                tripDao.findByIdCode(
                        result.getLong("trip_id"),
                        result.getStatement().getConnection()).orElse(null)
        );
    }
}
