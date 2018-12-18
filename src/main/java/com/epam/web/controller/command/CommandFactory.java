package com.epam.web.controller.command;

import com.epam.web.repository.UserRepository;
import com.epam.web.repository.factory.RepositoryFactory;
import com.epam.web.service.impl.ProductServiceImpl;
import com.epam.web.service.impl.UserServiceImpl;

public class CommandFactory {



    public static Command create(String command) {

        switch (command) {
            case CommandName.LOG_IN:
                return new LoginCommand(new UserServiceImpl(RepositoryFactory.getUserRepository()));
            case CommandName.REGISTRATION:
                return new RegistrationCommand(new UserServiceImpl(RepositoryFactory.getUserRepository()));
            case CommandName.LOG_OUT:
                return new LogOutCommand();
            case CommandName.SHOW_CATEGORY_PRODUCTS:
                return new ShowCategoryProductsCommand(new ProductServiceImpl(RepositoryFactory.getProductRepository()));
            default: {
                throw new UnsupportedOperationException("Unknown command" + command);
            }
        }

    }
}
