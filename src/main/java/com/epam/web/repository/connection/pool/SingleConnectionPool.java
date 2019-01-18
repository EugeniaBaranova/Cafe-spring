package com.epam.web.repository.connection.pool;

import com.epam.web.repository.exception.CloseConnectionException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleConnectionPool implements ConnectionPool {

    private static final Logger logger = Logger.getLogger(SingleConnectionPool.class);

    private Connection connection;

    private ConnectionPoolCreator poolCreator = ConnectionPoolCreator.getInstance();

    private AtomicBoolean initialized = new AtomicBoolean(false);

    public SingleConnectionPool() {
        init();
    }

    @Override
    public void init() {
        if (!initialized.get()) {
            connection = poolCreator.createSingleConnection(this);
            initialized.set(true);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean returnConnection(Connection connection, boolean forClosing) {
        if (connection != null) {
            this.connection = connection;
            if(forClosing){
                closeAll();
            }
            return true;
        }
        return false;
    }

    @Override
    public void closeAll() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new CloseConnectionException(e.getMessage(), e);
        }
    }
}
