package com.epam.web.controller.listener;

import com.epam.web.controller.constant.SessionAttribute;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(SessionAttribute.USER_ROLE, "guest");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
