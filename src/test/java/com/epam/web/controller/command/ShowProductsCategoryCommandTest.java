package com.epam.web.controller.command;

import com.epam.web.controller.command.product.ShowProductsCategoryCommand;
import com.epam.web.controller.constant.Pages;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.product.ProductBuilder;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowProductsCategoryCommandTest {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private ProductService productService = mock(ProductService.class);

    private ShowProductsCategoryCommand categoryProductsCommand = new ShowProductsCategoryCommand(productService);

    @Test
    @Ignore
    public void shouldForwardToMenuPageWhenCategoryProductsAreExist() throws ServiceException {
        //given
        List<Product> categoryProducts = Collections.singletonList(
                new ProductBuilder()
                .build());
        when(request.getParameter(any(String.class))).thenReturn(null);
        when(productService.findByCategory(null)).thenReturn(categoryProducts);
        doNothing().when(request).setAttribute(any(String.class), any(Object.class));
        //when
        CommandResult result = categoryProductsCommand.execute(request, response);
        //then
        Assert.assertFalse(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.MENU_PAGE));
    }

    @Test
    @Ignore
    public void shouldForwardToPageNotFoundWhenCategoryProductsAreNotExist() throws ServiceException {
        //given
        List<Product> categoryProducts = Collections.emptyList();
        when(request.getParameter(any(String.class))).thenReturn(null);
        when(productService.findByCategory(null)).thenReturn(categoryProducts);
        doNothing().when(request).setAttribute(any(String.class), any(Object.class));
        //when
        CommandResult result = categoryProductsCommand.execute(request, response);
        //then
        Assert.assertFalse(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.PAGE_NOT_FOUND));
    }
}
