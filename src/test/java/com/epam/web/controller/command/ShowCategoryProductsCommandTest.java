package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.entity.Product;
import com.epam.web.entity.ProductBuilder;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowCategoryProductsCommandTest {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);
    private ProductService productService = mock(ProductService.class);

    private ShowCategoryProductsCommand categoryProductsCommand = new ShowCategoryProductsCommand(productService);

    @Test
    public void shouldRedirectToMenuPageWhenCategoryProductsAreExist() throws ServiceException {
        //given
        List<Product> categoryProducts = Collections.singletonList(
                new ProductBuilder()
                .createProduct());
        when(request.getParameter(any(String.class))).thenReturn(null);
        when(productService.findByCategory(null)).thenReturn(categoryProducts);
        when(request.getSession(true)).thenReturn(session);
        doNothing().when(session).setAttribute(any(String.class), any(Object.class));
        //when
        CommandResult result = categoryProductsCommand.execute(request, response);
        //then
        Assert.assertTrue(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.MENU_PAGE));
    }

    @Test
    public void shouldRedirectToPageNotFoundWhenCategoryProductsAreNotExist() throws ServiceException {
        //given
        List<Product> categoryProducts = Collections.emptyList();
        when(request.getParameter(any(String.class))).thenReturn(null);
        when(productService.findByCategory(null)).thenReturn(categoryProducts);
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute(any(String.class), any(Object.class));
        //when
        CommandResult result = categoryProductsCommand.execute(request, response);
        //then
        Assert.assertTrue(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.PAGE_NOT_FOUND));
    }
}
