package ru.itpark.implementation.util;

import ru.itpark.framework.exception.DataStoreException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    private static <T> T executeInternal(DataSource dataSource, String sql, PreparedStatementExecutor<T> executor) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                return executor.execute(statement);
            }
        } catch (SQLException e) {
            throw new DataStoreException(e);
        }
    }

    public static <T> List<T> executeQuery(DataSource dataSource, String sql, PreparedStatementSetter setter, RowMapper<T> mapper) {
        return executeInternal(dataSource, sql, stmt -> {
            try (ResultSet resultSet = setter.setValues(stmt).executeQuery()) {
                List<T> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(mapper.map(resultSet));
                }
                return list;
            }
        });
    }

    public static <T> List<T> executeQuery(DataSource dataSource, String sql, RowMapper<T> rowMapper) throws SQLException {
        return executeQuery(dataSource, sql, stmt -> stmt, rowMapper);
    }

    public static int executeUpdate(DataSource dataSource, String sql, PreparedStatementSetter setter) {
        return executeInternal(dataSource, sql, stmt -> setter.setValues(stmt).executeUpdate());
    }

    public static int executeUpdate(DataSource dataSource, String sql) {
        return executeUpdate(dataSource, sql, stmt -> stmt);
    }
}

