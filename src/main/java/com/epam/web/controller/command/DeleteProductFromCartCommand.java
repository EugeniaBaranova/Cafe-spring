package com.epam.web.controller.command;

import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteProductFromCartCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        HttpSession session = req.getSession();
        String productId = req.getParameter(RequestParameter.ID);
        if (productId != null) {
            List<Long> productIds = (List<Long>) session.getAttribute(SessionAttribute.CART_PRODUCTS);
            List<Long> newProductIds = removeAllById(Long.valueOf(productId), productIds);
            session.setAttribute(SessionAttribute.CART_PRODUCTS, newProductIds);
        }
        return CommandResult.redirect("/controller?command=show_cart");
    }

    private List<Long> removeAllById(Long id, List<Long> products) {
        return products
                .stream()
                .filter(productId -> productId != id)
                .collect(Collectors.toList());

    }
}
