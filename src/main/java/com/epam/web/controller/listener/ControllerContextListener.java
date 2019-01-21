package com.epam.web.controller.listener;

import com.epam.web.config.DependencyConfiguration;
import com.epam.web.controller.command.CommandFactory;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.connection.pool.BaseConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ControllerContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ControllerContextListener.class);

    private static final String COMMAND_FACTORY_ATTRIBUTE = "commandFactory";

    private RepositorySource polledSource = BaseConnectionPool.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("[contextInitialized] Start to init connection pool");
        polledSource.init();
        logger.info("[contextInitialized] Connection pool initialized successfully");
        DependencyConfiguration dependencyConfiguration = new DependencyConfiguration();
        dependencyConfiguration.configure();
        logger.info("[contextInitialized] Dependency configured");
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(COMMAND_FACTORY_ATTRIBUTE, new CommandFactory());
        logger.info("[contextInitialized] Command factory created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("[contextInitialized] Start to close pool connections");
        polledSource.closeAll();
        logger.info("[contextInitialized] Pool connections closed");
    }
}
