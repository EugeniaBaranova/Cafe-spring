package com.epam.web.controller.command.product;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestAttribute;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.entity.product.Product;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ShowProductCommand implements Command {

    private ProductService productService;

    public ShowProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String id = req.getParameter(RequestParameter.ID);
        if(StringUtils.isNotEmpty(id) && StringUtils.isNumeral(id)){
            Optional<Product> productOptional = getProductService().findProduct(Long.valueOf(id));
            if(productOptional.isPresent()){
                Product product = productOptional.get();
                req.setAttribute(RequestAttribute.PRODUCT, product);
                return CommandResult.forward(Pages.PRODUCT_PAGE);
            }
        }
        return CommandResult.forward(Pages.PAGE_NOT_FOUND);
    }

    private ProductService getProductService() {
        return productService;
    }
}
