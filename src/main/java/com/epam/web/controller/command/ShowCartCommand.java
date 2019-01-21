package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.RequestAttribute;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.CartInfo;
import com.epam.web.entity.product.Product;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

public class ShowCartCommand implements Command {

    private ProductService productService;

    public ShowCartCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {

        HttpSession session = req.getSession();
        Object productsList = session.getAttribute(SessionAttribute.CART_PRODUCTS);
        Map<Product, Integer> productsMap = new HashMap<>();
        CartInfo cartInfo = new CartInfo();
        if (productsList != null) {
            List<Long> productIds = (List<Long>) productsList;
            List<Product> products = getProductService()
                    .findAllById(new HashSet<>(productIds));

            HashSet<Product> productSet = new HashSet<>(products);
            for (Product product : productSet) {
                Long id = product.getId();
                Integer productCount = getProductCount(id, products);
                if (!productsMap.containsKey(product)) {
                    productsMap.put(product, productCount);
                }
            }
            cartInfo.setProducts(productsMap);
        }
        req.setAttribute(RequestAttribute.CART_INFO, cartInfo);
        return CommandResult.forward(Pages.CART_PAGE);
    }

    private Integer getProductCount(Long id, List<Product> products) {
        List<Product> matchProducts = products
                .stream()
                .filter(product -> product.getId().equals(id))
                .collect(Collectors.toList());
        return matchProducts.size();

    }

    private ProductService getProductService() {
        return productService;
    }
}