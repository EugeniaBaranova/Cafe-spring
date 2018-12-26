package com.epam.web.controller.listener;

import com.epam.web.config.DependencyConfiguration;
import com.epam.web.controller.command.CommandFactory;
import com.epam.web.repository.connections.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ControllerContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ControllerContextListener.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        connectionPool.init();
        DependencyConfiguration dependencyConfiguration = new DependencyConfiguration();
        dependencyConfiguration.configure();
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("commandFactory", new CommandFactory());

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        connectionPool.closeAll();
    }
}
