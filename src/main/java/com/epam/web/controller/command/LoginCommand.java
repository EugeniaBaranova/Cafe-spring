package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.User;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {

        HttpSession session = req.getSession(true);

        String login = req.getParameter(RequestParameter.LOGIN);
        String password = req.getParameter(RequestParameter.PASSWORD);

        Optional<User> userOptional = userService.login(login, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            session.setAttribute(SessionAttribute.USER_ROLE, user.getRole());
            session.setAttribute(SessionAttribute.USER_BLOCK, user.isBlocked());
            session.setAttribute(SessionAttribute.USER_ID, user.getId());

            if (user.isBlocked()) {
                return CommandResult.redirect(Pages.LOGIN_PAGE);
            }
            return CommandResult.redirect(Pages.MAIN_PAGE);
        }
        session.setAttribute(SessionAttribute.UNKNOWN_USER, true);
        return CommandResult.redirect(Pages.LOGIN_PAGE);
    }
}
