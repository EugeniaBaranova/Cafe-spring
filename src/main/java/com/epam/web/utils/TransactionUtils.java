package com.epam.web.utils;

import com.epam.web.repository.connection.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtils {

    public static void beginTransaction(ConnectionPool connectionPool) throws SQLException {
        if (connectionPool != null) {
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            connectionPool.returnConnection(connection, false);
        }
    }

    public static void finishTransaction(ConnectionPool connectionPool, boolean withClosing) throws SQLException {
        if (connectionPool != null) {
            Connection connection = connectionPool.getConnection();
            connection.commit();
            connectionPool.returnConnection(connection, withClosing);
        }
    }

    public static void rollbackTransaction(ConnectionPool connectionPool, boolean withClosing) throws SQLException {
        if (connectionPool != null) {
            Connection connection = connectionPool.getConnection();
            connection.rollback();
            connectionPool.returnConnection(connection, withClosing);
        }

    }
}
