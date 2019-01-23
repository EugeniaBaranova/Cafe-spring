package com.epam.web.controller.command.cart;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ChangeCartItemCountCommand implements Command {


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String id = req.getParameter(RequestParameter.ID);

        if (StringUtils.isNotEmpty(id)) {
            if (StringUtils.isDigit(id)) {
                Long productId = Long.valueOf(id);
                HttpSession session = req.getSession();
                Object cartProducts = session.getAttribute(SessionAttribute.CART_PRODUCTS);
                if(cartProducts != null){
                    ((List<Long>)cartProducts)
                            .add(Long.valueOf(productId));
                }
            }


        }

        return null;
    }
}
