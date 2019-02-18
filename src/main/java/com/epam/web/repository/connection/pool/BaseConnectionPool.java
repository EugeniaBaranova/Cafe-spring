package com.epam.web.repository.connection.pool;

import com.epam.web.repository.connection.DatabasePropertyName;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.exception.CloseConnectionException;
import com.epam.web.repository.exception.ConnectionPoolException;
import com.epam.web.repository.exception.ConnectionPoolInitializationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class BaseConnectionPool implements RepositorySource, DataSource {

    private static final Logger logger = Logger.getLogger(BaseConnectionPool.class);
    private static final int CONNECTION_POOL_SIZE = 10;
    private ConnectionPoolCreator connectionPoolCreator;
    private BlockingQueue<Connection> connectionPool;

    private AtomicBoolean initialized = new AtomicBoolean(false);

    @Autowired
    public BaseConnectionPool(ConnectionPoolCreator connectionPoolCreator) {
        this.connectionPoolCreator = connectionPoolCreator;
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            if (!initialized.get()) {
                Properties properties = connectionPoolCreator.loadProperties();
                connectionPool = new ArrayBlockingQueue<>(CONNECTION_POOL_SIZE);
                Class.forName(
                        properties.getProperty(DatabasePropertyName.DRIVER_NAME));
                connectionPoolCreator.createConnections(connectionPool, CONNECTION_POOL_SIZE, this);
                initialized.set(true);
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolInitializationException(e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (connectionPool.isEmpty()) {
                return connectionPoolCreator.createSingleConnection(this);
            }
            return new ConnectionWrapper(connectionPool.take(), this);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean returnConnection(Connection connection, boolean forClosing) {
        try {
            if (connection != null) {
                if (forClosing) {
                    if (connectionPool.size() < CONNECTION_POOL_SIZE) {
                        connectionPool.put(connection);
                        return true;
                    } else {
                        connection.close();
                        return true;
                    }
                }
            }
            return false;
        } catch (InterruptedException | SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    @Override
    public void closeAll() {
        try {
            if (!connectionPool.isEmpty()) {
                for (Connection connection : connectionPool) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new CloseConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
