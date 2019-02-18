package com.epam.web.controller;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandFactory;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.utils.ServletResponseUtils;
import com.epam.web.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.web.controller.command.CommandName.*;


public class FrontController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(FrontController.class);

    private static final String COMMAND_FACTORY_ATTRIBUTE = "commandFactory";

    private CommandFactory commandFactory;

    public FrontController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!resp.isCommitted()) {
                String command = req.getParameter(RequestParameter.COMMAND);
                Command action = commandFactory.getCommand(command);
                if (action != null) {
                    CommandResult commandResult = action.execute(req, resp);
                    ServletResponseUtils.sendResponse(req, resp, commandResult, getServletContext());
                } else {
                    resp.sendError(404);
                }
            }
        } catch (Exception e) {
            logger.error("[process] Exception.", e);
            resp.sendError(500);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        Object commandFactory = servletContext.getAttribute(COMMAND_FACTORY_ATTRIBUTE);
        if (commandFactory != null) {
            this.commandFactory = (CommandFactory) commandFactory;
        }
        super.init(config);
    }
}
