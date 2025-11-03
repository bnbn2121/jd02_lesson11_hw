package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.model.News;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ViewNews implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("currentPage") != null) {
                request.setAttribute("currentPage", request.getParameter("currentPage"));
            }
            int newsId = Integer.parseInt(request.getParameter("newsId"));
            News foundedNews = newsService.getNewsById(newsId);
            request.setAttribute("news", foundedNews);
            request.getRequestDispatcher("/WEB-INF/jsp/news_view.jsp").forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=GO_TO_ERROR_PAGE");
        }
    }
}
