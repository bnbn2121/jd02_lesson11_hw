package com.edu.less08.filter;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.UserDao;
import com.edu.less08.dao.impl.RoleUtil;
import com.edu.less08.model.User;
import com.edu.less08.model.UserView;
import jakarta.annotation.Priority;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@WebFilter("/Controller")
@Priority(2)
public class RememberMeFilter extends HttpFilter {
    UserDao userDao = DaoProvider.getInstance().getUserDao();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(req, res);
            return;
        }

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            chain.doFilter(req, res);
            return;
        }

        Map<String, String> cookieMap = Arrays.stream(cookies)
                .collect(Collectors.toMap(
                        Cookie::getName,
                        Cookie::getValue
                ));


        if (cookieMap.get("rememberMe") != null) {
            try {
                int userId = Integer.parseInt(cookieMap.get("rememberMe"));
                UserView user = restoreUser(userId);
                if (user != null) {
                    req.getSession().setAttribute("user", user);
                } else {
                    clearRememberMeCockie(res);
                }
            } catch (NumberFormatException | DaoException e) {
                clearRememberMeCockie(res);
            }
        }
        chain.doFilter(req, res);
    }

    private UserView restoreUser(int userId) throws DaoException {
        Optional<User> userByIdOpt = userDao.getUserById(userId);
        if (userByIdOpt.isPresent()) {
            User userById = userByIdOpt.get();
            UserView user = new UserView(
                    userById.getId(),
                    userById.getLogin(),
                    userById.getEmail(),
                    RoleUtil.getRoleNameById(userById.getRoleId()).name()
            );
            return user;
        }
        return null;
    }

    private void clearRememberMeCockie(HttpServletResponse response) {
        Cookie rememberMe = new Cookie("rememberMe", "");
        rememberMe.setMaxAge(0);
        response.addCookie(rememberMe);
    }
}
