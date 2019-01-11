package com.epam.web.controller.filter;

import com.epam.web.controller.command.CommandName;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class UrlAccessFilter implements Filter {

    // TODO: 11.01.2019 or translocate to Page class? better?
    private List<String> notGuestURIs = Arrays.asList(
            Pages.PROFILE_PAGE, Pages.ADD_PRODUCT_PAGE);
    private List<String> notUserURIs = Arrays.asList(
            Pages.LOGIN_PAGE, Pages.REGISTRATION_PAGE,
            Pages.USER_LIST_PAGE, Pages.ADD_PRODUCT_PAGE);
    private List<String> notAdminURIs = Arrays.asList(
            Pages.LOGIN_PAGE, Pages.REGISTRATION_PAGE);
    private Map<UserRole, List<String>> forbiddenUserURIs = new HashMap<>();

    private List<String> notGuestCommands = Arrays.asList(
            CommandName.LOG_OUT, CommandName.ADD_NEW_PRODUCT,
            CommandName.SHOW_CART, CommandName.SHOW_ORDERS,
            CommandName.SHOW_USERS);
    private List<String> notUserCommands = Arrays.asList(
            CommandName.LOG_IN, CommandName.REGISTRATION,
            CommandName.SHOW_USERS, CommandName.ADD_NEW_PRODUCT);
    private List<String> notAdminCommands = Arrays.asList(
            CommandName.LOG_IN, CommandName.REGISTRATION);
    private Map<UserRole, List<String>> forbiddenUserCommands = new HashMap<>();

    {
        forbiddenUserURIs.put(UserRole.GUEST, notGuestURIs);
        forbiddenUserURIs.put(UserRole.USER, notUserURIs);
        forbiddenUserURIs.put(UserRole.ADMIN, notAdminURIs);

        forbiddenUserCommands.put(UserRole.GUEST, notGuestCommands);
        forbiddenUserCommands.put(UserRole.USER, notUserCommands);
        forbiddenUserCommands.put(UserRole.ADMIN, notAdminCommands);
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
        // TODO: 11.01.2019 not null
        if (Pages.CONTROLLER_URI.equals(uri)) {
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
