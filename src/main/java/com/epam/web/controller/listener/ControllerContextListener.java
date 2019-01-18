package com.epam.web.controller.listener;

import com.epam.web.config.DependencyConfiguration;
import com.epam.web.controller.command.CommandFactory;
import com.epam.web.repository.connection.pool.BaseConnectionPool;
import com.epam.web.repository.connection.pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ControllerContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ControllerContextListener.class);

    private static final String COMMAND_FACTORY_ATTRIBUTE = "commandFactory";

    private ConnectionPool connectionPool = BaseConnectionPool.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        connectionPool.init();
        DependencyConfiguration dependencyConfiguration = new DependencyConfiguration();
        dependencyConfiguration.configure();
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(COMMAND_FACTORY_ATTRIBUTE, new CommandFactory());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        connectionPool.closeAll();
    }
}
