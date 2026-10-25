package by.primech85.Dao;

import by.primech85.Dto.StationFilter;
import by.primech85.Exception.DaoException;
import by.primech85.entity.Station;
import by.primech85.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StationDao implements Dao<Station,Long>{
    private final static StationDao INSTANCE =new StationDao();
    private  final  static String SAVE_SQL =    """
                                                INSERT INTO station
                                                (code,city,name)
                                                values (?,?,?)
                                                """;
    private final static  String DELETE_SQL =  """
                                                delete from station
                                                where id = ?
                                                """;
    private final static  String FIND_ALL_SQL =  """
                                                select id,code,name,city from station
                                                """;
    private final static  String FIND_BY_ID_SQL = FIND_ALL_SQL + """
                                                where id = ?
                                                """;
    private final static  String FIND_BY_CODE_SQL = FIND_ALL_SQL + """
                                                where code = ?
                                                """;
    private final static String UPDATE_SQL = """
                                                update station
                                                set 
                                                    code = ?,
                                                    city = ?,
                                                    name = ?
                                                where id = ?
                                                """;
    private StationDao() {
    }
    public static StationDao getInstance(){
        return INSTANCE;
    }

    @Override
    public boolean update(Station station) {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setString(1,station.getCode());
            statement.setString(2,station.getCity());
            statement.setString(3,station.getName());
            statement.setLong(4,station.getId());
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Station> findAll() {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(FIND_ALL_SQL)){
            List<Station> station = new ArrayList<>();
            var result = statement.executeQuery();
            while(result.next()){
                station.add(
                        build(result)
                );

            }
            return station;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Station> findAll(StationFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.city() != null){
            parameters.add(filter.city());
            whereSql.add(" city = ? ");
        }
        if(filter.name() != null){
            parameters.add(filter.name());
            whereSql.add(" name = ? ");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                 parameters.size() > 2 ? " where " : " ",
                " LIMIT  ? OFFSET  ? "
        ));
        String sql = FIND_ALL_SQL +where;
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(sql)){
            List<Station> station = new ArrayList<>();
            for(int i = 0; i < parameters.size(); i++){
                statement.setObject(i+1,parameters.get(i));
            }
            System.out.println(statement);
            var result = statement.executeQuery();

            while(result.next()){
                station.add(
                        build(result)
                );

            }
            return station;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Station> findById(Long aLong) {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(FIND_BY_ID_SQL)){
            statement.setLong(1,aLong);
            var result = statement.executeQuery();
            Station station = null;
            if(result.next()){
                station = build(result);

            }
            return Optional.ofNullable(station);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Station> findByIdCode(String code, Connection connection) {
        try(var statement = connection.prepareStatement(FIND_BY_CODE_SQL)){
            statement.setString(1,code);
            var result = statement.executeQuery();
            Station station = null;
            if(result.next()){
                station = build(result);

            }
            return Optional.ofNullable(station);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  Station build(ResultSet result) throws SQLException {
        return new Station(
                result.getLong("id"),
                result.getString("code"),
                result.getString("city"),
                result.getString("name")
        );
    }

    @Override
    public Station save(Station station) {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(SAVE_SQL,Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,station.getCode());
            statement.setString(2,station.getCity());
            statement.setString(3,station.getName());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                station.setId(keys.getLong("id"));
            }
            return station;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(DELETE_SQL)){
            statement.setLong(1,aLong);
            return statement.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





}
