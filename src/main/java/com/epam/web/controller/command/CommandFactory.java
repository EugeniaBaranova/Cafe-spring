package com.epam.web.controller.command;

import com.epam.web.repository.UserRepository;
import com.epam.web.repository.factory.RepositoryFactory;
import com.epam.web.service.impl.ProductServiceImpl;
import com.epam.web.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static Map<String, Command> commands = new HashMap<>();

    // TODO: 21.12.2018 better?
    static {
        commands.put(CommandName.LOG_IN, new LoginCommand(new UserServiceImpl(RepositoryFactory.getUserRepository())));
        commands.put(CommandName.REGISTRATION, new RegistrationCommand(new UserServiceImpl(RepositoryFactory.getUserRepository())));
        commands.put(CommandName.LOG_OUT, new LogOutCommand());
        commands.put(CommandName.SHOW_CATEGORY_PRODUCTS, new ShowCategoryProductsCommand(new ProductServiceImpl(RepositoryFactory.getProductRepository())));
    }

    public static Command create(String commandName) {
        // TODO: 21.12.2018 check if null?
        return commands.get(commandName);
    }
}
