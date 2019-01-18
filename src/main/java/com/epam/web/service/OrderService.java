package com.epam.web.service;

import com.epam.web.entity.order.Order;
import com.epam.web.entity.order.OrderContext;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;
import com.epam.web.service.exception.ServiceException;

import java.util.List;

public interface OrderService extends Service {

    Order makeOrder(OrderContext orderContext) throws ServiceException;

}
