package com.epam.web.controller.command.order;

import com.epam.web.config.annotation.UriCommand;
import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandName;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.order.Order;
import com.epam.web.entity.order.OrderContext;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;
import com.epam.web.service.OrderService;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UriCommand(name = CommandName.MAKE_ORDER)
@Component
public class MakeOrderCommand implements Command {

    private static final Logger logger = Logger.getLogger(MakeOrderCommand.class);

    private OrderService orderService;

    private ProductService productService;

    @Autowired
    public MakeOrderCommand(@Qualifier("orderServiceImpl") OrderService orderService,
                            @Qualifier("productServiceImpl") ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {

        HttpSession session = req.getSession();
        Object productsInfo = session.getAttribute(SessionAttribute.CART_PRODUCTS);
        if (productsInfo != null) {
            String method = req.getParameter(RequestParameter.PAYMENT_METHOD);
            if (isAvailablePaymentMethod(method)) {
                PaymentMethod paymentMethod = PaymentMethod.valueOf(method);
                String receivingDate = req.getParameter(RequestParameter.RECEIVING_DATE);
                LocalDate date = getReceivingDate(receivingDate);
                Object user = session.getAttribute(SessionAttribute.USER);
                if (user != null) {
                    if (productsInfo instanceof Iterable) {
                        List<Long> cartIds = (List<Long>) productsInfo;
                        Set<Long> ids = new HashSet<>((List<Long>) productsInfo);
                        List<Product> products = getProductService().findAllByIdWithoutImage(ids);
                        OrderContext orderContext = new OrderContext(date, products, paymentMethod, (User) user, cartIds);
                        Order order = getOrderService().makeOrder(orderContext);
                        if (order != null) {
                            session.setAttribute(SessionAttribute.CART_PRODUCTS, new ArrayList<>());
                            return CommandResult.redirect("/controller?command=show_orders");
                        }
                    }
                }
            }
        }
        return CommandResult.redirect(Pages.LOGIN_PAGE);
    }

    private LocalDate getReceivingDate(String rawDate) {
        return LocalDate.parse(rawDate);
    }


    private boolean isAvailablePaymentMethod(String method) {
        if (method != null) {
            for (PaymentMethod paymentMethod : PaymentMethod.values()) {
                if (paymentMethod.name().equals(method)) {
                    return true;
                }
            }
        }
        return false;
    }


    private ProductService getProductService() {
        return productService;
    }


    private OrderService getOrderService() {
        return orderService;
    }
}
