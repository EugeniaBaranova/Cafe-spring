package com.epam.web.controller.command.cart;

import com.epam.web.config.annotation.UriCommand;
import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandName;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.service.CartService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@UriCommand(name = CommandName.CHANGE_CART_COUNT)
@Component
public class ChangeCartItemCountCommand implements Command {

    private CartService cartService;

    @Autowired
    public ChangeCartItemCountCommand(@Qualifier("cartServiceImpl") CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String id = req.getParameter(RequestParameter.ID);
        String amountString = req.getParameter(RequestParameter.AMOUNT);
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(amountString)) {
            if (StringUtils.isDigit(id) && StringUtils.isDigit(amountString)) {
                Integer newAmount = Integer.valueOf(amountString);
                Long productId = Long.valueOf(id);
                HttpSession session = req.getSession();
                Object cartProducts = session.getAttribute(SessionAttribute.CART_PRODUCTS);
                if (cartProducts != null) {
                    List<Long> cartProduct = (List<Long>) cartProducts;
                    this.getCartService().updateCartProductsListAmount(productId, cartProduct, newAmount);
                    return CommandResult.forward("/controller?command=show_cart");
                }
            }
        }
        return CommandResult.forward(Pages.PAGE_NOT_FOUND);
    }

    private CartService getCartService() {
        return cartService;
    }
}
