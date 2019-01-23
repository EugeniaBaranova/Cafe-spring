package com.epam.web.controller.command.cart;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeCartItemCountCommand implements Command {


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
                    Integer existProductCount = getProductCount(productId, cartProduct);
                    int deltaAmount = newAmount - existProductCount;
                    if (deltaAmount > 0) {
                        for (int i = 0; i < deltaAmount; i++) {
                            cartProduct.add(productId);
                        }
                    } else if (deltaAmount < 0) {
                        for (int i = 0; i < Math.abs(deltaAmount); i++) {
                            cartProduct.remove(productId);
                        }
                    }
                    return CommandResult.forward("/controller?command=show_cart");
                }
            }
        }
        return CommandResult.forward(Pages.PAGE_NOT_FOUND);
    }

    private Integer getProductCount(Long id, List<Long> products) {
        List<Long> matchProducts = products
                .stream()
                .filter(productId -> productId == id)
                .collect(Collectors.toList());
        return matchProducts.size();

    }
}
