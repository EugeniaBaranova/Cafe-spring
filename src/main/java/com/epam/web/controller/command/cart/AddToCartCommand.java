package com.epam.web.controller.command.cart;

import com.epam.web.config.annotation.UriCommand;
import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandName;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.service.exception.ServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@UriCommand(name = CommandName.ADD_TO_CART)
@Component
public class AddToCartCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        HttpSession session = req.getSession();
        String productId = req.getParameter(RequestParameter.ID);
        String requestPage = req.getParameter(RequestParameter.REQUEST_PAGE);
        Object cartProducts = session.getAttribute(SessionAttribute.CART_PRODUCTS);
        if (cartProducts != null) {
            ((List<Long>) cartProducts)
                    .add(Long.valueOf(productId));
        }
        return CommandResult.redirect(requestPage);
    }
}
