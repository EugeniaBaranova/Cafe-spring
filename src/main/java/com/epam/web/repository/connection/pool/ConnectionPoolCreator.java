package com.epam.web.repository.connection.pool;

import com.epam.web.repository.connection.DatabasePropertyName;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolCreator {

    private static final Logger logger = Logger.getLogger(ConnectionPoolCreator.class);

    private static final String PROPERTIES_FILE_NAME = "database-access.properties";

    private static final ConnectionPoolCreator instance = new ConnectionPoolCreator();

    private Properties properties = new Properties();

    private ConnectionPoolCreator() {
    }

    public static ConnectionPoolCreator getInstance() {
        return instance;
    }

    public void createConnections(BlockingQueue<Connection> connectionPool, int count, RepositorySource connectionPoolKeeper) {
        try {
            for (int i = 0; i < count; i++) {
                connectionPool.put(createSingleConnection(connectionPoolKeeper));
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    public Connection createSingleConnection(RepositorySource connectionPoolKeeper) {
        try {
            return new ConnectionWrapper(DriverManager.getConnection(
                    properties.getProperty(DatabasePropertyName.URL),
                    properties.getProperty(DatabasePropertyName.LOGIN),
                    properties.getProperty(DatabasePropertyName.PASSWORD)),
                    connectionPoolKeeper);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    public Properties loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }


}
