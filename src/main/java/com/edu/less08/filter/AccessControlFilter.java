package com.edu.less08.filter;

import com.edu.less08.controller.CommandName;
import com.edu.less08.controller.CommandProvider;
import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.impl.DBRoleDAO;
import com.edu.less08.model.UserRole;
import com.edu.less08.model.UserView;
import jakarta.annotation.Priority;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/Controller")
@Priority(3)
public class AccessControlFilter extends HttpFilter {
    private final List<CommandName> authorizedUserCommands;
    private final List<CommandName> moderatorCommands;
    private final List<CommandName> adminCommands;

    {
        authorizedUserCommands = new ArrayList<>();
        authorizedUserCommands.add(CommandName.ADD_COMMENT);
        authorizedUserCommands.add(CommandName.EDIT_COMMENT);
        authorizedUserCommands.add(CommandName.REMOVE_COMMENT);
        authorizedUserCommands.add(CommandName.ADD_NEWS);
        authorizedUserCommands.add(CommandName.ADD_COMPLAINT);

        moderatorCommands = new ArrayList<>();
        moderatorCommands.add(CommandName.APPROVE_NEWS);
        moderatorCommands.add(CommandName.EDIT_NEWS);
        moderatorCommands.add(CommandName.REMOVE_NEWS);
        moderatorCommands.add(CommandName.PROCESS_COMPLAINT);

        adminCommands = new ArrayList<>();
        adminCommands.add(CommandName.SET_MODERATOR);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        CommandName commandName = CommandProvider.getCommandName(req.getParameter("command"));
        UserRole role = getRole(session);
        if (hasAccess(commandName, role)) {
            chain.doFilter(req, res);
        } else {
            redirectToMainPageWithErrorAccess(req, res);
        }
    }

    private UserRole getRole(HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            return UserRole.GUEST;
        } else {
            String roleName = ((UserView)session.getAttribute("user")).getRole();
            return UserRole.valueOf(roleName);
        }
    }

    private boolean hasAccess(CommandName commandName, UserRole role) {
        if(adminCommands.contains(commandName)) {
            if (role == UserRole.SUPERADMIN || role == UserRole.ADMIN) {
                return true;
            } else {
                return false;
            }
        }
        if(moderatorCommands.contains(commandName)) {
            if (role == UserRole.SUPERADMIN || role == UserRole.ADMIN || role == UserRole.MODERATOR) {
                return true;
            } else {
                return false;
            }
        }
        if(authorizedUserCommands.contains(commandName)) {
            if (role == UserRole.GUEST) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    private void redirectToMainPageWithErrorAccess(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.getSession().setAttribute("errorMessage", "You do not have sufficient permissions to perform this action");
        res.sendRedirect("Controller?command=go_to_main_page");
    }
}
