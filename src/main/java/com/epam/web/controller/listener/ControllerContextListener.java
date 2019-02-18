package com.epam.web.controller.listener;

import com.epam.web.config.DependencyConfiguration;

import com.epam.web.controller.command.CommandFactory;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.connection.pool.BaseConnectionPool;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ControllerContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ControllerContextListener.class);

    private static final String COMMAND_FACTORY_ATTRIBUTE = "commandFactory";

    private GenericApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(DependencyConfiguration.class);
        this.applicationContext = applicationContext;
        CommandFactory commandFactory = applicationContext.getBean(COMMAND_FACTORY_ATTRIBUTE, CommandFactory.class);

        logger.info("[contextInitialized] Start to init connection pool");
        //polledSource.init();
        logger.info("[contextInitialized] Connection pool initialized successfully");

        logger.info("[contextInitialized] Dependency configured");
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(COMMAND_FACTORY_ATTRIBUTE, commandFactory);
        servletContext.setAttribute("applicationContext", applicationContext);
        logger.info("[contextInitialized] Command factory created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("[contextInitialized] Start to close pool connections");
        if (this.applicationContext != null) {
            RepositorySource baseConnectionPool =
                    applicationContext.getBean("baseConnectionPool", RepositorySource.class);
            baseConnectionPool.closeAll();
        }
        logger.info("[contextInitialized] Pool connections closed");
    }
}
