package com.epam.web.service.impl.cart;

import com.epam.web.service.CartService;

import java.util.List;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    @Override
    public void updateCartProductsListAmount(Long productId, List<Long> oldProduct, int amount) {
        Integer existProductCount = getProductCount(productId, oldProduct);
        int deltaAmount = amount - existProductCount;
        if (deltaAmount > 0) {
            for (int i = 0; i < deltaAmount; i++) {
                oldProduct.add(productId);
            }
        } else if (deltaAmount < 0) {
            for (int i = 0; i < Math.abs(deltaAmount); i++) {
                oldProduct.remove(productId);
            }
        }
    }


    private Integer getProductCount(Long id, List<Long> products) {
        List<Long> matchProducts = products
                .stream()
                .filter(productId -> productId == id)
                .collect(Collectors.toList());
        return matchProducts.size();

    }
}
