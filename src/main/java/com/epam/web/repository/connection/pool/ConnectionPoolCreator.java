package com.epam.web.repository.connection.pool;

import com.epam.web.repository.connection.DatabasePropertyName;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.exception.ConnectionPoolException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

@Component
public class ConnectionPoolCreator {

    private static final Logger logger = Logger.getLogger(ConnectionPoolCreator.class);

    private static final String PROPERTIES_FILE_NAME = "database-access.properties";

    private Properties properties = new Properties();

    public ConnectionPoolCreator() {
    }

    public void createConnections(BlockingQueue<Connection> connectionPool, int count, RepositorySource repositorySource) {
        try {
            for (int i = 0; i < count; i++) {
                connectionPool.put(createSingleConnection(repositorySource));
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    public Connection createSingleConnection(RepositorySource repositorySource) {
        try {
            return new ConnectionWrapper(DriverManager.getConnection(
                    properties.getProperty(DatabasePropertyName.URL),
                    properties.getProperty(DatabasePropertyName.LOGIN),
                    properties.getProperty(DatabasePropertyName.PASSWORD)),
                    repositorySource);
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
