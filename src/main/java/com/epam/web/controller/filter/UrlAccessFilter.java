package com.epam.web.controller.filter;

import com.epam.web.controller.command.CommandName;
import com.epam.web.controller.constant.Pages;
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

public class UrlAccessFilter implements Filter {

    private final List<String> NOT_GUEST_URI_S = Arrays.asList(
            Pages.PROFILE_PAGE, Pages.ADD_PRODUCT_PAGE,
            Pages.INTERNAL_ERROR_PAGE);
    private final List<String> NOT_USER_URI_S = Arrays.asList(
            Pages.LOGIN_PAGE, Pages.REGISTRATION_PAGE,
            Pages.USER_LIST_PAGE, Pages.ADD_PRODUCT_PAGE,
            Pages.INTERNAL_ERROR_PAGE);
    private final List<String> NOT_ADMIN_URI_S = Arrays.asList(
            Pages.LOGIN_PAGE, Pages.REGISTRATION_PAGE,
            Pages.INTERNAL_ERROR_PAGE);
    //todo final? CONSTANT_NAME?
    private Map<UserRole, List<String>> forbiddenUserURIs = new HashMap<>();

    private final List<String> NOT_GUEST_COMMANDS = Arrays.asList(
            CommandName.LOG_OUT, CommandName.ADD_NEW_PRODUCT,
            CommandName.SHOW_CART, CommandName.SHOW_ORDERS,
            CommandName.SHOW_USERS);
    private final List<String> NOT_USER_COMMANDS = Arrays.asList(
            CommandName.LOG_IN, CommandName.REGISTRATION,
            CommandName.SHOW_USERS, CommandName.ADD_NEW_PRODUCT);
    private final List<String> NOT_ADMIN_COMMANDS = Arrays.asList(
            CommandName.LOG_IN, CommandName.REGISTRATION);
    private Map<UserRole, List<String>> forbiddenUserCommands = new HashMap<>();

    {
        forbiddenUserURIs.put(UserRole.GUEST, NOT_GUEST_URI_S);
        forbiddenUserURIs.put(UserRole.USER, NOT_USER_URI_S);
        forbiddenUserURIs.put(UserRole.ADMIN, NOT_ADMIN_URI_S);

        forbiddenUserCommands.put(UserRole.GUEST, NOT_GUEST_COMMANDS);
        forbiddenUserCommands.put(UserRole.USER, NOT_USER_COMMANDS);
        forbiddenUserCommands.put(UserRole.ADMIN, NOT_ADMIN_COMMANDS);
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
        if (StringUtils.isNotEmpty(commandName) && Pages.CONTROLLER_URI.equals(uri)) {
            return isNotAvailableCommand(forbiddenUserCommands.get(userRole), commandName);
        }
        return isNotAvailableURI(uri, forbiddenUserURIs.get(userRole));
    }

    private boolean isNotAvailableCommand(List<String> forbiddenCommands, String commandName) {
        for (String forbiddenCommand : forbiddenCommands) {
            if(forbiddenCommand.equals(commandName)){
                return true;
            }
        }
        return false;
    }

    private boolean isNotAvailableURI(String uri, List<String> forbiddenURIs) {
        for (String forbiddenURI : forbiddenURIs) {
            if (forbiddenURI.equals(uri)) {
                return true;
            }
        }
        return false;
    }

}
