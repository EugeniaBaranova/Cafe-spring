package com.epam.web.controller.filter;

import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class SessionParameterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isNotRegistrationPage(path) && isNotIconLoadURI(path)) {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
            removeAttributeIfExist(session, SessionAttribute.UNSUCCESSFUL_REGISTRATION);
            removeAttributeIfExist(session, SessionAttribute.REGISTRATION_ERRORS);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean isNotIconLoadURI(String path) {
        return !Pages.ICON_LOAD_URI.equals(path);
    }

    private boolean isNotRegistrationPage(String path) {
        return !path.startsWith(Pages.REGISTRATION_PAGE);
    }

    private void removeAttributeIfExist(HttpSession session, String sessionAttributeName) {
        Object sessionAttribute = session.getAttribute(sessionAttributeName);
        if (sessionAttribute != null) {
            session.removeAttribute(sessionAttributeName);
        }
    }
}
