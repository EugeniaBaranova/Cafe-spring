package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException{
        HttpSession session = req.getSession();
        session.invalidate();
        return CommandResult.redirect(Pages.LOGIN_PAGE);
    }
}
