package com.epam.web.repository.connection.pool;

import java.sql.Connection;

public interface ConnectionPool {

    void init();

    Connection getConnection();

    boolean returnConnection(Connection connection, boolean forClosing);

    void closeAll();
}
