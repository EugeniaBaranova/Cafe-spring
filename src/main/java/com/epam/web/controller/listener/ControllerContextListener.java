package com.epam.web.controller.listener;

import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ControllerContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ControllerContextListener.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        connectionPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        connectionPool.closeAll();
    }
}
