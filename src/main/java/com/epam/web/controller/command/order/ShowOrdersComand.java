package com.epam.web.controller.command.order;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestAttribute;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.order.Order;
import com.epam.web.entity.user.User;
import com.epam.web.service.OrderService;
import com.epam.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowOrdersComand implements Command {

    private OrderService orderService;

    public ShowOrdersComand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {

        HttpSession session = req.getSession();
        Object user = session.getAttribute(SessionAttribute.USER);
        if (user != null) {

            List<Order> allOrder = getOrderService()
                    .getAllByUserId(((User) user).getId());
            req.setAttribute(RequestAttribute.ORDERS, allOrder);
            return CommandResult.forward(Pages.ORDERS_PAGE);
        }

        return CommandResult.forward(Pages.PAGE_NOT_FOUND);
    }

    private OrderService getOrderService() {
        return orderService;
    }
}
