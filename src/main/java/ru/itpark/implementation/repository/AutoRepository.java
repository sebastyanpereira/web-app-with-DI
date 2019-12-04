package ru.itpark.implementation.repository;

import ru.itpark.framework.annotation.Component;
import ru.itpark.implementation.domain.Auto;
import ru.itpark.implementation.util.JdbcTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public class AutoRepository {
    private final DataSource ds;

    public AutoRepository() throws NamingException {
        InitialContext context = new InitialContext();
        ds = (DataSource) context.lookup("java:/comp/env/jdbc/db");
        String sql = "CREATE TABLE IF NOT EXISTS autos (id TEXT PRIMARY KEY, name TEXT NOT NULL, description TEXT NOT NULL, image TEXT);";
        JdbcTemplate.executeUpdate(ds, sql);
    }

    public List<Auto> getAll() throws SQLException {
        String sql = "SELECT id, name, description, image FROM autos";
        return JdbcTemplate.executeQuery(ds, sql, rs ->
                new Auto(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image")
                )
        );
    }

    public void create(String name, String description, String image) {
        String sql = "INSERT INTO autos (id, name, description, image) VALUES (?, ?, ?, ?)";
        JdbcTemplate.executeUpdate(ds, sql, stmt -> {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.setString(4, image);
            return stmt;
        });
    }

    public List<Auto> search(String text) {
        String sql = "SELECT id, name, description, image FROM autos WHERE name LIKE ? OR description LIKE ?;";
        return JdbcTemplate.executeQuery(ds, sql, stmt -> {
            stmt.setString(1, "%" + text + "%");
            stmt.setString(2, "%" + text + "%");
            return stmt;
        }, rs ->
                new Auto(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image")
                ));
    }
}
