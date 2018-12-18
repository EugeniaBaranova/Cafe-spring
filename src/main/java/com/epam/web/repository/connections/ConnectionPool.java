package com.epam.web.repository.connections;

import com.epam.web.repository.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private static final ConnectionPool instance = new ConnectionPool();

    private static final int CONNECTION_POOL_SIZE = 10;
    private static final String PROPERTIES_FILE_NAME = "database-access";

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);

    private BlockingQueue<Connection> connectionPool;

    //TODO or boolean??
    private AtomicBoolean initialized = new AtomicBoolean(false);

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void init() throws ConnectionPoolException {
        try {
            if (!initialized.get()) {
                connectionPool = new ArrayBlockingQueue<>(CONNECTION_POOL_SIZE);
                Class.forName(
                        resourceBundle.getString(
                                DatabasePropertyName.DRIVER_NAME));
                createConnections(CONNECTION_POOL_SIZE);
                initialized.set(true);
            }
        } catch (ClassNotFoundException | ConnectionPoolException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            if (connectionPool.isEmpty()) {
                return createSingleConnection();
            }
            return connectionPool.take();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    public boolean returnConnection(Connection connection) throws ConnectionPoolException {
        try {
            if (connection != null) {
                if (connectionPool.size() < CONNECTION_POOL_SIZE) {
                    connectionPool.put(connection);
                    return true;
                } else {
                    connection.close();
                    return true;
                }
            }
            return false;
        } catch (InterruptedException | SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    public void closeAll() throws ConnectionPoolException {
        try {
            if (!connectionPool.isEmpty()) {
                for (Connection connection : connectionPool) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    private void createConnections(int count) throws ConnectionPoolException {
        try {
            for (int i = 0; i < count; i++) {
                connectionPool.put(createSingleConnection());
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    private Connection createSingleConnection() throws ConnectionPoolException {
        try {
            return DriverManager.getConnection(
                    resourceBundle.getString(DatabasePropertyName.URL),
                    resourceBundle.getString(DatabasePropertyName.LOGIN),
                    resourceBundle.getString(DatabasePropertyName.PASSWORD));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

}
