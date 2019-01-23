package com.epam.web.controller.command.user;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.RequestAttribute;
import com.epam.web.utils.StringUtils;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.user.User;
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
        String login = req.getParameter(RequestParameter.LOGIN);
        String password = req.getParameter(RequestParameter.PASSWORD);

        if (!StringUtils.isEmpty(login) && !StringUtils.isEmpty(password)) {
            Optional<User> userOptional = getUserService().login(login, password);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                HttpSession session = req.getSession(true);
                session.setAttribute(SessionAttribute.USER_BLOCK, user.isBlocked());
                if (user.isBlocked()) {
                    return CommandResult.redirect(Pages.LOGIN_PAGE);
                }
                session.setAttribute(SessionAttribute.USER_ROLE, user.getRole());
                session.setAttribute(SessionAttribute.USER, user);
                return CommandResult.redirect(Pages.MAIN_PAGE);
            }
        }
        req.setAttribute(RequestAttribute.UNKNOWN_USER, true);
        return CommandResult.forward(Pages.LOGIN_PAGE);
    }

    private UserService getUserService() {
        return userService;
    }
}
