package ru.itpark.implementation.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementExecutor<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
