package com.epam.web.repository.connections;

import com.epam.web.repository.exception.CloseConnectionException;
import com.epam.web.repository.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;

public class ConnectionWrapper implements Closeable {

    private static final Logger logger = Logger.getLogger(ConnectionWrapper.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    private Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws IOException {
        try {
            connectionPool.returnConnection(connection);
        } catch (ConnectionPoolException e) {
            logger.error(e.getMessage(), e);
            throw new CloseConnectionException(e.getMessage(), e);
        }
    }

    public PreparedStatement prepareStatement(String sqlQuery) throws SQLException {
        return connection.prepareStatement(sqlQuery);
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }


    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }
}
