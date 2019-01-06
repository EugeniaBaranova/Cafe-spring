package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestAttribute;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.entity.product.Product;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCategoryProductsCommand implements Command {

    private ProductService productService;

    ShowCategoryProductsCommand(ProductService productService) {
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
