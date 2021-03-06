package com.epam.web.controller.command;

import com.epam.web.controller.command.user.LogoutCommand;
import com.epam.web.controller.constant.Pages;
import com.epam.web.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogoutCommandTest {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);

    private LogoutCommand logoutCommand = new LogoutCommand();

    @Test
    @Ignore
    public void shouldRedirectToLoginPageWhenLogOutCommand() throws ServiceException {
        //given
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).invalidate();
        //when
        CommandResult result = logoutCommand.execute(request, response);
        //then
        Assert.assertTrue(result.isRedirect());
        Assert.assertThat(result.getPage(), is(Pages.LOGIN_PAGE));
    }
}
