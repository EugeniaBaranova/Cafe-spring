package com.epam.web.controller.command;

import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddToCartCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        //TODO command
        HttpSession session = req.getSession(true);
        String id = req.getParameter(RequestParameter.ID);
        String request_page = req.getParameter(RequestParameter.REQUEST_PAGE);

        return null;
    }
}
