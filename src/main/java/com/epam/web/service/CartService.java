package com.epam.web.service;

import java.util.List;

public interface CartService extends Service {

    void updateCartProductsListAmount(Long productId, List<Long> oldProduct, int amount);

}
