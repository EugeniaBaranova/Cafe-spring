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
        try {
            connectionPool.init();
        } catch (ConnectionPoolException e) {
            //TODO throw something(unchecked) or not?
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            connectionPool.closeAll();
            //https://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered
        } catch (ConnectionPoolException e) {
            //TODO throw something(unchecked) or not?
            logger.error(e.getMessage(), e);
        }
    }
}
