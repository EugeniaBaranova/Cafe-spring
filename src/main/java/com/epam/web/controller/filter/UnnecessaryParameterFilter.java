package com.epam.web.controller.filter;

import com.epam.web.controller.constant.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UnnecessaryParameterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        removeAttributeIfExist(session, SessionAttribute.UNSUCCESSFUL_REGISTRATION);
        removeAttributeIfExist(session, SessionAttribute.UNKNOWN_USER);
        removeAttributeIfExist(session, SessionAttribute.CATEGORY_PRODUCTS);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void removeAttributeIfExist(HttpSession session, String sessionAttributeName) {
        Object sessionAttribute = session.getAttribute(sessionAttributeName);
        if (sessionAttribute != null) {
            session.removeAttribute(sessionAttributeName);
        }
    }

    @Override
    public void destroy() {

    }
}
