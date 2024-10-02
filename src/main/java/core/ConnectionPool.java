package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConnectionPool {

    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 3;
    static ResourceBundle rb;

    static {
        try {
            // 파일 확장자와 경로를 제거하고 기본적으로 'mysql'로 설정
            rb = ResourceBundle.getBundle("mysql", Locale.KOREA);
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded: " + rb.getString("mysql.url")); // 확인용 출력
        } catch (MissingResourceException e) {
            System.out.println("mysql.properties 파일을 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool create() throws SQLException {

        String url = rb.getString("mysql.url");
        String user = rb.getString("mysql.username");
        String password = rb.getString("mysql.password");

        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new ConnectionPool(pool);
    }

    public ConnectionPool(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Connection getConnection() {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public int getUseSize() {
        return connectionPool.size();
    }
}
