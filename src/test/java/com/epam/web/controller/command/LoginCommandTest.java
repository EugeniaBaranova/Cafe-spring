package com.epam.web.controller.command;

import com.epam.web.controller.constant.Pages;
import com.epam.web.entity.User;
import com.epam.web.entity.UserBuilder;
import com.epam.web.entity.enums.UserRole;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class LoginCommandTest {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);
    private UserService userService = mock(UserService.class);

    private Command loginCommand = new LoginCommand(userService);


    @Before
    public void setUp(){
        when(request.getSession(true)).thenReturn(session);
    }

    @Test
    @Ignore
    public void shouldRedirectToMainPageWhenUserExistAndNotBlocked() throws ServiceException {
        //given
        User testUser = new UserBuilder()
                .setRole(UserRole.USER)
                .createUser();
        when(userService.login(null, null)).thenReturn(Optional.of(testUser));

        //when
        CommandResult result = loginCommand.execute(request, response);
        //then
        Assert.assertTrue(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.MAIN_PAGE));
    }

    @Test
    public void shouldRedirectToLoginPageWhenUserNotExist() throws ServiceException {
        //given
        when(userService.login(null, null)).thenReturn(Optional.empty());
        //when
        CommandResult result = loginCommand.execute(request, response);
        //then
        Assert.assertTrue(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.LOGIN_PAGE));
    }

    @Test
    @Ignore
    public void shouldRedirectToLoginPageWhenUserIsBlocked() throws ServiceException {
        //given
        User testUser = new UserBuilder()
                .setBlocked(true)
                .setRole(UserRole.USER)
                .createUser();

        when(userService.login(null, null)).thenReturn(Optional.of(testUser));
        //when
        CommandResult result = loginCommand.execute(request, response);
        //then
        Assert.assertTrue(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.LOGIN_PAGE));

    }
}
