package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.service.CommentService;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteComment implements Command {
    CommentService commentService = ServiceProvider.getInstance().getCommentService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int commentId = Integer.parseInt(request.getParameter("commentId"));
            String newsId = request.getParameter("newsId");
            String currentPage = request.getParameter("currentPage");
            commentService.deleteComment(commentId);
            response.sendRedirect("Controller?command=view_news&currentPage=%s&newsId=%s".formatted(currentPage, newsId));
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=GO_TO_ERROR_PAGE");
        }
    }
}
