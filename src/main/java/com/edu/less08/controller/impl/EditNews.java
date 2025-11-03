package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.model.News;
import com.edu.less08.model.Status;
import com.edu.less08.model.UserView;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EditNews implements Command {
    private NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int newsId = Integer.parseInt(request.getParameter("newsId"));
            String newTitle = request.getParameter("title");
            String newBrief = request.getParameter("brief");
            String newContent = request.getParameter("content");
            String newImagePath = request.getParameter("imagePath");
            Status newStatus = Status.ACTIVE; // потом поменять на Awaiting для ожидания аппрува
            News updatedNews = newsService.updateNews(newsId, newTitle, newBrief, newContent, newImagePath, newStatus);
            String currentPage = request.getParameter("currentPage");
            response.sendRedirect("Controller?command=View_News&newsId=%d&currentPage=%s".formatted(newsId, currentPage));
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=GO_TO_ERROR_PAGE");
        }
    }
}
