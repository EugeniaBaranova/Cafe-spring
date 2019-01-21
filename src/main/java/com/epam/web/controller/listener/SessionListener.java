package com.epam.web.controller.listener;

import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.enums.UserRole;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(SessionAttribute.USER_ROLE, UserRole.GUEST);
        session.setAttribute(SessionAttribute.CART_PRODUCTS, new ArrayList<>());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
