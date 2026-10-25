package by.primech85.Dao;

import by.primech85.entity.Station;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<K,V> {
    boolean update(K k);
    List<K> findAll();
    Optional<K> findById(V v);
    K save(K k);
    boolean delete(V v);
    K build(ResultSet result) throws SQLException;

}
