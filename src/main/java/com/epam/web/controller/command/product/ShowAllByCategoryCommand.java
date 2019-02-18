package com.epam.web.controller.command.product;

import com.epam.web.config.annotation.UriCommand;
import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandName;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestAttribute;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.entity.product.Product;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@UriCommand(name = "showAllByCategoryCommand")
@Component
public class ShowAllByCategoryCommand implements Command {

    private ProductService productService;

    @Autowired
    public ShowAllByCategoryCommand(@Qualifier("productServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String category = req.getParameter(RequestParameter.PRODUCT_CATEGORY);
        List<Product> categoryProducts = getProductService().findByCategory(category);
        if (!categoryProducts.isEmpty()) {
            req.setAttribute(RequestAttribute.CATEGORY_PRODUCTS, categoryProducts);
            return CommandResult.forward(Pages.MENU_PAGE);
        }
        return CommandResult.forward(Pages.PAGE_NOT_FOUND);
    }

    private ProductService getProductService() {
        return productService;
    }
}
