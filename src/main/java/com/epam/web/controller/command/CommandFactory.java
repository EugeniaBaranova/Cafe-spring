package com.epam.web.controller.command;

import com.epam.web.controller.command.cart.AddToCartCommand;
import com.epam.web.controller.command.cart.ChangeCartItemCountCommand;
import com.epam.web.controller.command.cart.DeleteFromCartCommand;
import com.epam.web.controller.command.cart.ShowCartCommand;
import com.epam.web.controller.command.order.MakeOrderCommand;
import com.epam.web.controller.command.order.ShowOrdersComand;
import com.epam.web.controller.command.product.AddProductCommand;
import com.epam.web.controller.command.product.ShowProductsCategoryCommand;
import com.epam.web.controller.command.product.ShowProductCommand;
import com.epam.web.controller.command.user.RegisterUserCommand;
import com.epam.web.controller.command.user.LogoutCommand;
import com.epam.web.controller.command.user.LoginCommand;
import com.epam.web.service.CartService;
import com.epam.web.service.OrderService;
import com.epam.web.service.ProductService;
import com.epam.web.service.UserService;
import com.epam.web.service.factory.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> commands = new HashMap<>();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    public CommandFactory() {
        commands.put(CommandName.LOG_IN, new LoginCommand(getServiceFactory().getService(UserService.class)));
        commands.put(CommandName.REGISTRATION, new RegisterUserCommand(
                getServiceFactory().getService(UserService.class)));
        commands.put(CommandName.LOG_OUT, new LogoutCommand());
        commands.put(CommandName.SHOW_CATEGORY_PRODUCTS, new ShowProductsCategoryCommand(
                getServiceFactory().getService(ProductService.class)));
        commands.put(CommandName.ADD_NEW_PRODUCT, new AddProductCommand(
                getServiceFactory().getService(ProductService.class)));
        commands.put(CommandName.SHOW_PRODUCT, new ShowProductCommand(
                getServiceFactory().getService(ProductService.class)));
        commands.put(CommandName.MAKE_ORDER, new MakeOrderCommand(
                getServiceFactory().getService(OrderService.class),
                getServiceFactory().getService(ProductService.class)));
        commands.put(CommandName.ADD_TO_CART, new AddToCartCommand());
        commands.put(CommandName.SHOW_CART, new ShowCartCommand(
                getServiceFactory().getService(ProductService.class)));
        commands.put(CommandName.DELETE_FROM_CART, new DeleteFromCartCommand());
        commands.put(CommandName.SHOW_ORDERS, new ShowOrdersComand
                (getServiceFactory().getService(OrderService.class)));
        commands.put(CommandName.CHANGE_CART_COUNT, new ChangeCartItemCountCommand(
                getServiceFactory().getService(CartService.class)));
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    private ServiceFactory getServiceFactory() {
        return serviceFactory;
    }
}
