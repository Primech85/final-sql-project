package by.primech85.Dao;

import by.primech85.Dto.Tripfilter;
import by.primech85.entity.Station;
import by.primech85.entity.Status_type;
import by.primech85.entity.Trip;
import by.primech85.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TripDao implements Dao<Trip,Long> {
    private static final TripDao INSTANCE = new TripDao();
    private final StationDao stationDao = StationDao.getInstance();
    private TripDao(){

    }
    public static TripDao getInstance(){
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            update trip
            set 
                arrival_code = ?,
                arrival_date = ?,
                departure_code = ?,
                departure_date = ?,
                status = ? :: status_type,
                train_id = ?,
                trip_no = ?
            where id = ?
            """;
    private final static String FIND_ALL_SQL = """
            select id,arrival_code,arrival_date,departure_code,departure_date,status,train_id,trip_no from trip
            """;
    private final static String FIND_BY_ID_SQL= FIND_ALL_SQL + """
            where id = ?
            """;
    private final static String SAVE_SQL = """
            INSERT INTO trip
            (arrival_code,arrival_date,departure_code,departure_date,status,train_id,trip_no)
            values (?,?,?,?,?:: status_type,?,?)
            """;
    private final static String DELETE_SQL = """
            delete from trip
            where id = ?
            """;
    private final static  String FIND_BY_TRAIN_ID_SQL = FIND_ALL_SQL + """
                                                where id = ?
                                                """;
    @Override
    public boolean update(Trip trip) {
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setObject(1,trip.getArrival_code());
            statement.setObject(2,trip.getArrival_date());
            statement.setString(3,trip.getDeparture_code());
            statement.setObject(4,trip.getDeparture_date());
            statement.setObject(5,trip.getStatus());
            statement.setLong(6,trip.getTrain_id());
            statement.setString(7,trip.getTrip_no());
            statement.setLong(8,trip.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Trip> findAll() {
        List<Trip> trip = new ArrayList<>();
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var result = statement.executeQuery();
            while(result.next()){
                trip.add(build(result));
            }
            return trip;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Trip> findAll(Tripfilter filter) {
        List<Object> parametres = new ArrayList<>();
        List<String> wgeresql = new ArrayList<>();
        if(filter.arrival_code()!=null){
            parametres.add(filter.arrival_code());
            wgeresql.add(" arrival_code = ? ");
        }
        if(filter.departure_code()!= null){
            parametres.add(filter.departure_code());
            wgeresql.add(" departure_code = ? ");
        }
        if(filter.status()!= null){
            parametres.add(filter.status());
            wgeresql.add(" status = ? :: status_type ");
        }
        if(filter.train_id()!= null){
            parametres.add(filter.train_id());
            wgeresql.add(" train_id = ? ");
        }
        parametres.add(filter.limit());
        parametres.add(filter.offset());
        var sql = FIND_ALL_SQL + wgeresql.stream().collect(Collectors.joining(
                " AND ",
                parametres.size() > 2 ? " WHERE " : " ",
                " LIMIT ? OFFSET ?"
        ));
        List<Trip> trip = new ArrayList<>();
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(sql)) {
            for( int i = 0; i < parametres.size(); i ++){
                statement.setObject(i+1, parametres.get(i));
            }
            var result = statement.executeQuery();
            while(result.next()){
                trip.add(build(result));
            }
            return trip;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Trip> findById(Long aLong) {
        Trip trip = new Trip();
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1,aLong);
            var result = statement.executeQuery();
            if(result.next()){
                trip = build(result);
            }
            return Optional.ofNullable(trip);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Trip> findByIdCode(Long id, Connection connection) {
        try(var statement = connection.prepareStatement(FIND_BY_TRAIN_ID_SQL)){
            statement.setLong(1,id);
            var result = statement.executeQuery();
            System.out.println(statement);
            Trip trip = null;
            if(result.next()){
                trip = build(result);

            }
            return Optional.ofNullable(trip);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trip save(Trip trip) {
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setObject(1,trip.getArrival_code());
            statement.setObject(2,trip.getArrival_date());
            statement.setString(3,trip.getDeparture_code());
            statement.setObject(4,trip.getDeparture_date());
            statement.setObject(5,trip.getStatus());
            statement.setLong(6,trip.getTrain_id());
            statement.setString(7,trip.getTrip_no());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                trip.setId(keys.getLong("id"));
            }
            return trip;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(DELETE_SQL))
        {
            statement.setLong(1,aLong);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trip build(ResultSet result) throws SQLException {
        /*Trip trip = new Trip();
        trip.setId(result.getLong("id"));
        trip.setArrival_code(result.getString("arrival_code"));
        trip.setArrival_date(result.getObject("arrival_date", LocalDateTime.class));
        trip.setDeparture_code(result.getString("departure_code"));
        trip.setDeparture_date(result.getObject("departure_date", LocalDateTime.class));
        trip.setStatus(Status_type.valueOf(result.getString("status")));
        trip.setTrain_id(result.getLong("train_id"));
        trip.setTrip_no(result.getString("trip_no"));*/
        return new Trip(result.getLong("id"),
        stationDao.findByIdCode(
                result.getString("arrival_code"),
                result.getStatement().getConnection()
                ).orElse(null),
        result.getObject("arrival_date", LocalDateTime.class),
        result.getString("departure_code"),
        result.getObject("departure_date", LocalDateTime.class),
        Status_type.valueOf(result.getString("status")),
        result.getLong("train_id"),
        result.getString("trip_no")

        );
    }
}
