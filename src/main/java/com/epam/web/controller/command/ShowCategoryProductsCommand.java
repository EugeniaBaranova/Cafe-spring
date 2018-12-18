package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.Product;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowCategoryProductsCommand implements Command {

    private ProductService productService;

    public ShowCategoryProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String category = req.getParameter(RequestParameter.PRODUCT_CATEGORY);

        List<Product> categoryProducts = productService.findByCategory(category);
        if (!categoryProducts.isEmpty()) {
            HttpSession session = req.getSession(true);
            session.setAttribute(SessionAttribute.CATEGORY_PRODUCTS, categoryProducts);
            return CommandResult.redirect(Pages.MENU_PAGE);
        }
        return CommandResult.redirect(Pages.PAGE_NOT_FOUND);
    }
}
