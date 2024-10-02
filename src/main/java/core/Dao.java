package core;

import java.sql.Connection;
import java.util.List;

public interface Dao<K, V> {
    V insert(V entity, Connection conn) throws Exception;
    V update(V entity, Connection conn) throws Exception;
    boolean delete(K key, Connection conn) throws Exception;
    V select(K key, Connection conn) throws Exception;
    List<V> selectAll(Connection conn) throws Exception;
}
