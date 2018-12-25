package com.epam.web.controller.command;

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
        commands.put(CommandName.REGISTRATION, new RegistrationCommand(getServiceFactory().getService(UserService.class)));
        commands.put(CommandName.LOG_OUT, new LogOutCommand());
        commands.put(CommandName.SHOW_CATEGORY_PRODUCTS, new ShowCategoryProductsCommand(
                getServiceFactory().getService(ProductService.class)));
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    private ServiceFactory getServiceFactory(){
        return serviceFactory;
    }
}
