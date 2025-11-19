package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.model.UserView;
import com.edu.less08.service.ServiceProvider;
import com.edu.less08.service.UserSecurityService;
import com.edu.less08.service.ServiceException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DoAuth implements Command {
    private final UserSecurityService userSecurity = ServiceProvider.getInstance().getUserSecurityService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        try {
            UserView user = userSecurity.authenticate(userLogin, userPassword);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("rememberMe", rememberMe);
            if (rememberMe != null) {
                addCookieWithTimeLife("rememberMe", String.valueOf(user.getId()), 14, response);
            }
            response.sendRedirect("Controller?command=go_to_main_page");
        } catch (ServiceException e) {
            request.getSession().setAttribute("errorMessage", e.getUserMessage());
            response.sendRedirect("Controller?command=go_to_auth_page");
        }
    }

    private void addCookieWithTimeLife(String name, String value, int maxAgeDays, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeDays * 24 * 60 * 60);
        response.addCookie(cookie);
    }
}
