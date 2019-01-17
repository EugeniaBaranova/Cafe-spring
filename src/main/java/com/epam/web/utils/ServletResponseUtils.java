package com.epam.web.utils;

import com.epam.web.controller.command.CommandResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletResponseUtils {

    public static void sendResponse(HttpServletRequest request,
                                    HttpServletResponse response,
                                    CommandResult commandResult,
                                    ServletContext servletContext) throws IOException, ServletException {
        String page = commandResult.getPage();
        if (commandResult.isRedirect()) {
            response.sendRedirect(page);
            return;
        }
        dispatch(request, response, page, servletContext);
    }

    private static void dispatch(HttpServletRequest req,
                                 HttpServletResponse resp,
                                 String page,
                                 ServletContext servletContext) throws ServletException, IOException {
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }
}
