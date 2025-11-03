package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.dao.impl.NewsDaoDB;
import com.edu.less08.model.News;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToNewsRedactorPage implements Command {
    private NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("currentPage") != null) {
                request.setAttribute("currentPage", request.getParameter("currentPage"));
            }
            String newsIdParameter = request.getParameter("newsId");
            if (newsIdParameter != null) {
                int newsId = Integer.parseInt(newsIdParameter);
                News foundedNews = newsService.getNewsById(newsId);
                System.out.println(foundedNews.getContent());
                request.setAttribute("news", foundedNews);
            }
            request.getRequestDispatcher("/WEB-INF/jsp/news_redactor.jsp").forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=GO_TO_ERROR_PAGE");
        }
    }
}
