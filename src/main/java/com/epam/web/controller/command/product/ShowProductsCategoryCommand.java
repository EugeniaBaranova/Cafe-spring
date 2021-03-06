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
import com.epam.web.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@UriCommand(name = CommandName.SHOW_CATEGORY_PRODUCTS)
@Component
public class ShowProductsCategoryCommand implements Command {

    private static final int COUNT_ON_PAGE = 8;
    private static final int ZERO_DIVISION_REMAINDER = 0;

    private ProductService productService;

    @Autowired
    public ShowProductsCategoryCommand(@Qualifier("productServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String category = req.getParameter(RequestParameter.PRODUCT_CATEGORY);
        String currentPageString = req.getParameter(RequestParameter.CURRENT_PAGE);
        if (StringUtils.isNotEmpty(category) && StringUtils.isNumeral(currentPageString)) {
            List<Integer> pages = new ArrayList<>();
            getPageNumbersByCategory(category, pages);
            Integer currentPage = Integer.valueOf(currentPageString);
            if (pages.contains(currentPage)) {
                List<Product> categoryProducts = getProductService().findByCategory(category, currentPage, COUNT_ON_PAGE);
                if (!categoryProducts.isEmpty()) {
                    req.setAttribute(RequestAttribute.CATEGORY_PRODUCTS, categoryProducts);
                    req.setAttribute(RequestAttribute.PAGES, pages);
                    return CommandResult.forward(Pages.MENU_PAGE);
                }
            }
        }
        return CommandResult.forward(Pages.PAGE_NOT_FOUND);
    }

    private void getPageNumbersByCategory(String category, List<Integer> pages) throws ServiceException {
        if (category != null && pages != null) {
            int productAmount = getProductService().amountInCategory(category);
            int pagesCount;
            int intDivisionResult = productAmount / COUNT_ON_PAGE;
            if ((productAmount % COUNT_ON_PAGE) == ZERO_DIVISION_REMAINDER) {
                pagesCount = intDivisionResult;
            } else {
                pagesCount = intDivisionResult + 1;
            }
            for (int pageNumber = 1; pagesCount >= pageNumber; pageNumber++) {
                pages.add(pageNumber);
            }
        }
    }

    private ProductService getProductService() {
        return productService;
    }
}
