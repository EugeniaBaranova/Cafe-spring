package com.epam.web.controller.command;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MakeOrderCommand implements Command {

    private static final Logger logger = Logger.getLogger(MakeOrderCommand.class);

    private OrderService orderService;

    private ProductService productService;

    public MakeOrderCommand(OrderService orderService, ProductService productService) {
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
                        Set<Long> ids = new HashSet<>((List<Long>) productsInfo);
                        List<Product> products = getProductService().findAllById(ids);
                        OrderContext orderContext = new OrderContext(date, products, paymentMethod, (User) user);
                        Order order = getOrderService().makeOrder(orderContext);
                        if(order != null){
                            return CommandResult.redirect(Pages.ORDERS_PAGE);
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
