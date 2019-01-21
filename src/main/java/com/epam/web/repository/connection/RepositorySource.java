package com.epam.web.repository.connection;

import java.sql.Connection;

public interface RepositorySource {

    void init();

    Connection getConnection();

    boolean returnConnection(Connection connection, boolean forClosing);

    void closeAll();

}
