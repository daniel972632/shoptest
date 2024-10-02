package customer.dao;

import customer.dto.Customer;
import customer.sql.SQL_Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements core.Dao<String, Customer> {

    @Override
    public Customer insert(Customer customer, Connection conn) throws SQLException {
        String query = SQL_Customer.me_in_ed; // SQL 파일에서 쿼리 호출
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getCustomerId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getPassword());
            pstmt.setString(6, customer.getLevel());
            pstmt.executeUpdate();
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer, Connection conn) throws SQLException {
        String query = SQL_Customer.me_in_pw; // SQL 파일에서 쿼리 호출
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getPassword());
            pstmt.setString(2, customer.getCustomerId());
            pstmt.executeUpdate();
        }
        return customer;
    }

    @Override
    public boolean delete(String customerId, Connection conn) throws SQLException {
        String query = SQL_Customer.me_ad_ma; // SQL 파일에서 쿼리 호출
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public Customer select(String customerId, Connection conn) throws SQLException {
        String query = SQL_Customer.me_or_vi; // SQL 파일에서 쿼리 호출
        Customer customer = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer(
                            rs.getString("customer_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getString("level"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                }
            }
        }
        return customer;
    }

    @Override
    public List<Customer> selectAll(Connection conn) throws SQLException {
        String query = SQL_Customer.me_or_de; // SQL 파일에서 쿼리 호출
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("level"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        }
        return customers;
    }
}
