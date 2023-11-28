package repository.book;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import domain.Book;

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

        createBookTable();
    }


    private void createBookTable() {
        String QUERY = "CREATE TABLE IF NOT EXISTS books (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR," +
                "author VARCHAR," +
                "year INTEGER," +
                "language VARCHAR," +
                "originalPublication VARCHAR)";
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(QUERY)
        ){
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Book> findOne(Integer id) {
        Book book = null;
        String QUERY = "SELECT * FROM books WHERE id = ?";
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(QUERY)
        ){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("language"),
                        rs.getString("originalPublication"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void save(Book book) {
        String QUERY = "INSERT INTO books (id, title, author, year, language, originalPublication) VALUES (?, ?,?,?,?,?)";
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(QUERY)
        ){
            ps.setInt(1, book.id());
            ps.setString(2, book.title());
            ps.setString(3, book.author());
            ps.setInt(4, book.year());
            ps.setString(5, book.language());
            ps.setString(6, book.originalPublication());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
