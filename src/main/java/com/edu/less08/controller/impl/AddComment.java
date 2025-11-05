package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.dao.CommentDao;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.model.Comment;
import com.edu.less08.model.UserView;
import com.edu.less08.service.CommentService;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AddComment implements Command {
    CommentService commentService = ServiceProvider.getInstance().getCommentService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("commentText").isBlank()) {
            return;
        }
        try {
            String newsIdParameter = request.getParameter("newsId");
            String userIdParameter = request.getParameter("userId");
            if (newsIdParameter != null) {
                addNewsComment(request, response);
            } else if (newsIdParameter != null) {
                addUserComment(request, response);
            }
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=go_to_error_page");
        }
    }

    private void addNewsComment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        int newsId = Integer.parseInt(request.getParameter("newsId"));
        String commentText = request.getParameter("commentText");
        UserView user = (UserView) request.getSession().getAttribute("user");
        String currentPage = request.getParameter("currentPage");
        commentService.addNewsComment(newsId, commentText, user.getId());
        response.sendRedirect("Controller?command=VIEW_NEWS&newsId=%d&currentPage=%s".formatted(newsId, currentPage));
    }

    private void addUserComment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        //реализация
    }
}
