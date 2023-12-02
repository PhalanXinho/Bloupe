package repository.book;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class PostgreSQLBookRepository implements BookRepository {


    private final HikariDataSource connectionPool;

    public PostgreSQLBookRepository() {

        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("hikari.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HikariConfig config = new HikariConfig(properties);
        connectionPool = new HikariDataSource(config);
    }

    @Override
    public Optional<Book> findById(int id) {
        Book book = null;
        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getDate("release_date"), rs.getString("language") );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }
}
