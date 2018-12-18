package com.epam.web.controller;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandFactory;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestAttribute;
import com.epam.web.controller.constant.RequestParameter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FrontController extends HttpServlet {

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
            String command = req.getParameter(RequestParameter.COMMAND);
            Command action = CommandFactory.create(command);
            CommandResult commandResult = action.execute(req, resp);
            sendResponse(req, resp, commandResult);
        } catch (Exception e) {
            req.setAttribute(RequestAttribute.ERROR_MASSAGE, e.getMessage());
            dispatch(req, resp, Pages.ERROR_PAGE);
        }
    }

    private void sendResponse(HttpServletRequest request, HttpServletResponse response, CommandResult commandResult) throws IOException, ServletException {
        String page = commandResult.getPage();
        if (commandResult.isRedirect()) {
            response.sendRedirect(page);
            return;
        }
        dispatch(request, response, page);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }

}
