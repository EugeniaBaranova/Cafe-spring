package com.epam.web.controller.command.user;

import com.epam.web.config.annotation.UriCommand;
import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandName;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.service.exception.ServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@UriCommand(name = CommandName.LOG_OUT)
@Component
public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException{
        HttpSession session = req.getSession();
        session.invalidate();
        return CommandResult.redirect(Pages.LOGIN_PAGE);
    }
}
