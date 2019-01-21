package com.epam.web.controller.filter;

import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.enums.UserRole;
import com.epam.web.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.epam.web.controller.command.CommandName.*;
import static com.epam.web.controller.constant.Pages.*;
import static com.epam.web.entity.enums.UserRole.*;

public class UrlAccessFilter implements Filter {

    private final Map<String, List<UserRole>> NOT_AVAILABLE_PAGES = new HashMap<>();

    private final Map<String, List<UserRole>> NOT_AVAILABLE_CONTROLLER_COMMANDS = new HashMap<>();

    private final Map<String, List<UserRole>> AVAILABLE_IMAGE_CONTENT_COMMANDS = new HashMap<>();

    {
        NOT_AVAILABLE_PAGES.put(PROFILE_PAGE, Arrays.asList(GUEST));
        NOT_AVAILABLE_PAGES.put(INTERNAL_ERROR_PAGE, Arrays.asList(GUEST, USER, ADMIN));
        NOT_AVAILABLE_PAGES.put(LOGIN_PAGE, Arrays.asList(USER, ADMIN));
        NOT_AVAILABLE_PAGES.put(REGISTRATION_PAGE, Arrays.asList(USER, ADMIN));
        NOT_AVAILABLE_PAGES.put(ADD_PRODUCT_PAGE, Arrays.asList(GUEST, USER));
        NOT_AVAILABLE_PAGES.put(USERS_PAGE, Arrays.asList(GUEST, USER));
        NOT_AVAILABLE_PAGES.put(EDIT_PRODUCT_PAGE, Arrays.asList(GUEST, USER));
        NOT_AVAILABLE_PAGES.put(ORDERS_PAGE, Arrays.asList(GUEST, ADMIN));
        NOT_AVAILABLE_PAGES.put(CART_PAGE, Arrays.asList(GUEST, ADMIN));

        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(LOG_IN, Arrays.asList(USER, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(REGISTRATION, Arrays.asList(USER, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(LOG_OUT, Arrays.asList(GUEST));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(SHOW_ORDERS, Arrays.asList(GUEST, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(SHOW_CART, Arrays.asList(GUEST, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(SHOW_USERS, Arrays.asList(GUEST, USER));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(ADD_NEW_PRODUCT, Arrays.asList(GUEST, USER, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(EDIT_PRODUCT, Arrays.asList(GUEST, USER, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(DELETE_PRODUCT, Arrays.asList(GUEST, USER));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(MAKE_ORDER, Arrays.asList(GUEST, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(CHANGE_CART_COUNT, Arrays.asList(GUEST, ADMIN));
        NOT_AVAILABLE_CONTROLLER_COMMANDS.put(ADD_TO_CART, Arrays.asList(GUEST, ADMIN));

        AVAILABLE_IMAGE_CONTENT_COMMANDS.put(ADD_NEW_PRODUCT, Arrays.asList(GUEST, USER));
        AVAILABLE_IMAGE_CONTENT_COMMANDS.put(EDIT_PRODUCT, Arrays.asList(GUEST, USER));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        Object role = session.getAttribute(SessionAttribute.USER_ROLE);
        if (role != null) {
            UserRole userRole = (UserRole) role;
            String uri = ((HttpServletRequest) servletRequest).getRequestURI();
            String commandName = servletRequest.getParameter(RequestParameter.COMMAND);
            if (NotHaveAccess(userRole, uri, commandName)) {
                ((HttpServletResponse) servletResponse).sendError(404);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean NotHaveAccess(UserRole userRole, String uri, String commandName) {
        if (StringUtils.isNotEmpty(commandName)) {
            switch (uri) {
                case CONTROLLER_URI:
                    return isNotAvailableFor(userRole, NOT_AVAILABLE_CONTROLLER_COMMANDS.get(commandName));
                case IMAGE_CONTENT_URI:
                    return !isNotAvailableFor(userRole, AVAILABLE_IMAGE_CONTENT_COMMANDS.get(commandName));
            }
        }
        return isNotAvailableFor(userRole, NOT_AVAILABLE_PAGES.get(uri));
    }

    private boolean isNotAvailableFor(UserRole userRole, List<UserRole> accessRoles) {
        if (userRole != null && accessRoles != null) {
            for (UserRole accessRole : accessRoles) {
                if (accessRole.equals(userRole)) {
                    return true;
                }
            }
        }
        return false;
    }
}
