package com.edu.less08.filter;

import com.edu.less08.model.UserView;
import jakarta.annotation.Priority;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@WebFilter("/Controller")
@Priority(2)
public class RememberMeFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                Map<String, String> cookieMap = Arrays.stream(cookies)
                        .collect(Collectors.toMap(
                                Cookie::getName,
                                Cookie::getValue
                        ));
                if (cookieMap.get("rememberMe") != null) {
                    UserView user = new UserView(Integer.parseInt(cookieMap.get("id")), cookieMap.get("login"), cookieMap.get("email"), cookieMap.get("role"));
                    req.getSession().setAttribute("user", user);
                }
            }
        }

        chain.doFilter(req, res);
    }
}
