package com.epam.web.controller.command;

import com.epam.web.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.epam.web.controller.command.CommandName.*;

@Component
public class CommandFactory {

    private Set<String> commandNames = new HashSet<>(
            Arrays.asList(LOG_IN, REGISTRATION, LOG_OUT, SHOW_CATEGORY_PRODUCTS, SHOW_ORDERS,
                    SHOW_CART, ADD_NEW_PRODUCT, SHOW_PRODUCT, MAKE_ORDER, CHANGE_CART_COUNT,
                    ADD_TO_CART, DELETE_FROM_CART));

    private Map<String, Command> commands = new HashMap<>();

    public Command getCommand(String commandName){
        if (StringUtils.isNotEmpty(commandName) && commandNames.contains(commandName)) {
            return commands.get(commandName);
        }
        return null;
    }
}
