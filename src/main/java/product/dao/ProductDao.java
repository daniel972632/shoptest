package product.dao;

import core.Dao;
import product.dto.Product;
import product.sql.SQL_Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements Dao<Integer, Product> {

    @Override
    public Product insert(Product entity, Connection conn) throws Exception {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_Product.me_ad_ma, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, entity.getCategory_id());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getDescription());
            pstmt.setInt(4, entity.getPrice());
            pstmt.setInt(5, entity.getStock_quantity());
            pstmt.setBoolean(6, entity.is_selling());
            pstmt.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis())); // created_at
            pstmt.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis())); // updated_at

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setProduct_id(generatedKeys.getInt(1)); // 자동 생성된 product_id 설정
                    }
                }
            }
            return entity; // 삽입된 엔티티 반환
        }
    }

    @Override
    public Product update(Product entity, Connection conn) throws Exception {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_Product.me_up_ad)) {
            pstmt.setInt(1, entity.getCategory_id());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getDescription());
            pstmt.setInt(4, entity.getPrice());
            pstmt.setInt(5, entity.getStock_quantity());
            pstmt.setBoolean(6, entity.is_selling());
            pstmt.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis())); // updated_at
            pstmt.setInt(8, entity.getProduct_id());

            pstmt.executeUpdate();
            return entity; // 업데이트된 엔티티 반환
        }
    }

    @Override
    public boolean delete(Integer key, Connection conn) throws Exception {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_Product.me_li_de)) {
            pstmt.setInt(1, key);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // 성공적으로 삭제되었으면 true 반환
        }
    }

    @Override
    public Product select(Integer key, Connection conn) throws Exception {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_Product.me_or_vi)) {
            pstmt.setInt(1, key);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getInt("category_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("price"),
                            rs.getInt("stock_quantity"),
                            rs.getBoolean("is_selling"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                }
            }
        }
        return null; // 제품이 없으면 null 반환
    }

    @Override
    public List<Product> selectAll(Connection conn) throws Exception {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_Product.me_or_de);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getInt("stock_quantity"),
                        rs.getBoolean("is_selling"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        }
        return products; // 모든 제품 리스트 반환
    }
}
