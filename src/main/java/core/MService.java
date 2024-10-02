package core;

import java.sql.Connection;
import java.util.List;

public interface MService<K, V> {
    V register(V entity, Connection conn) throws Exception;
    V modify(V entity, Connection conn) throws Exception;
    boolean remove(K key, Connection conn) throws Exception;
    V get(K key, Connection conn) throws Exception;
    List<V> getAll(Connection conn) throws Exception;
}