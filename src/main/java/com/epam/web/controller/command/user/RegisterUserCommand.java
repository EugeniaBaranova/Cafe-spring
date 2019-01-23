package com.epam.web.controller.command.user;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.SavingResult;
import com.epam.web.entity.user.User;
import com.epam.web.entity.user.UserBuilder;
import com.epam.web.entity.validation.Error;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class RegisterUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegisterUserCommand.class);
    private UserService userService;

    RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        HttpSession session = req.getSession(true);
        User registrationCandidate = convertFromReq(req);
        SavingResult<User> result = getUserService().register(registrationCandidate);
        if (result.hasError()) {
            Set<Error> errors = result.getErrors();
            session.setAttribute(SessionAttribute.REGISTRATION_ERRORS, errors);
            session.setAttribute(SessionAttribute.UNSUCCESSFUL_REGISTRATION, true);
            return CommandResult.redirect(Pages.REGISTRATION_PAGE);
        }
        User newUser = result.getData();
        session.setAttribute(SessionAttribute.USER_ROLE, newUser.getRole());
        session.setAttribute(SessionAttribute.USER, newUser);
        return CommandResult.redirect(Pages.PROFILE_PAGE);
    }

    private User convertFromReq(HttpServletRequest req){
        String login = req.getParameter(RequestParameter.LOGIN);
        String password = req.getParameter(RequestParameter.PASSWORD);
        String name = req.getParameter(RequestParameter.NAME);
        String email = req.getParameter(RequestParameter.EMAIL);
        return new UserBuilder()
                .setName(name)
                .setEmail(email)
                .setLogin(login)
                .setPassword(password)
                .createUser();
    }

    private UserService getUserService() {
        return userService;
    }
}
