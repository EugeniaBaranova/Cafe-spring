package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.User;
import com.epam.web.entity.UserBuilder;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements Command {

    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    private UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {

        String login = req.getParameter(RequestParameter.LOGIN);
        String password = req.getParameter(RequestParameter.PASSWORD);
        String name = req.getParameter(RequestParameter.NAME);
        String email = req.getParameter(RequestParameter.EMAIL);

        HttpSession session = req.getSession(true);

        User newUser = new UserBuilder()
                .setName(name)
                .setEmail(email)
                .setLogin(login)
                .setPassword(password)
                .createUser();

        //TODO another way?
        User addedUser = userService.addUser(newUser);
        if(addedUser!=null){
            return CommandResult.redirect(Pages.MAIN_PAGE);
        }
        session.setAttribute(SessionAttribute.UNSUCCESSFUL_REGISTRATION, true);
        return CommandResult.redirect(Pages.REGISTRATION_PAGE);
    }
}
