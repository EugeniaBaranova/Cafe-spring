package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.RegistrationResult;
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

import static com.epam.web.controller.constant.SessionAttribute.REGISTRATION_ERRORS;
import static com.epam.web.controller.constant.SessionAttribute.UNSUCCESSFUL_REGISTRATION;

public class UserRegistrationCommand implements Command {
    private static final Logger logger = Logger.getLogger(UserRegistrationCommand.class);
    private UserService userService;

    UserRegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        HttpSession session = req.getSession(true);
        User registrationCandidate = convertFromReq(req);
        RegistrationResult<User> result = getUserService().register(registrationCandidate);
        if (result.hasError()) {
            Set<Error> errors = result.getErrors();
            session.setAttribute(REGISTRATION_ERRORS, errors);
            session.setAttribute(UNSUCCESSFUL_REGISTRATION, true);
            return CommandResult.redirect(Pages.REGISTRATION_PAGE);
        }
        User newUser = result.getData();
        // TODO: 05.01.2019 Make some action with newUser. For example redirect to newUser profile.
        session.setAttribute(SessionAttribute.USER, newUser);
        return CommandResult.redirect(Pages.MAIN_PAGE);
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
