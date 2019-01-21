package com.epam.web.repository.connection.pool;

import com.epam.web.repository.connection.DatabasePropertyName;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.exception.CloseConnectionException;
import com.epam.web.repository.exception.ConnectionPoolException;
import com.epam.web.repository.exception.ConnectionPoolInitializationException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class BaseConnectionPool implements RepositorySource {

    private static final Logger logger = Logger.getLogger(BaseConnectionPool.class);
    private static final BaseConnectionPool instance = new BaseConnectionPool();
    private static final int CONNECTION_POOL_SIZE = 10;
    private ConnectionPoolCreator poolCreator = ConnectionPoolCreator.getInstance();
    private BlockingQueue<Connection> connectionPool;

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private BaseConnectionPool() {
    }

    public static BaseConnectionPool getInstance() {
        return instance;
    }

    @Override
    public void init() {
        try {
            if (!initialized.get()) {
                Properties properties = poolCreator.loadProperties();
                connectionPool = new ArrayBlockingQueue<>(CONNECTION_POOL_SIZE);
                Class.forName(
                        properties.getProperty(DatabasePropertyName.DRIVER_NAME));
                poolCreator.createConnections(connectionPool, CONNECTION_POOL_SIZE, instance);
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
                return poolCreator.createSingleConnection(instance);
            }
            return new ConnectionWrapper(connectionPool.take(), instance);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    @Override
    public boolean returnConnection(Connection connection, boolean forClosing) {
        try {
            if (connection != null) {
                if (!forClosing) {
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
}
