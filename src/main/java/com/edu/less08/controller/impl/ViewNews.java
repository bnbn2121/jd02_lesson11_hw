package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.model.News;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewNews implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int newsId = Integer.parseInt(request.getParameter("newsId"));
            HttpSession session = request.getSession();
            List<News> listNews = (ArrayList<News>) session.getAttribute("listNews");
            News foundedNews = null;
            if (listNews != null) {
                for (News news : listNews) {
                    if (news.getId() == newsId) {
                        foundedNews = news;
                        break;
                    }
                }
            }
            if (foundedNews == null) {
                foundedNews = newsService.getNewsById(newsId);
            }
            String returnPage = request.getParameter("returnPage");
            request.setAttribute("returnPage", returnPage);
            request.setAttribute("news", foundedNews);
            request.getRequestDispatcher("/WEB-INF/jsp/news_view.jsp").forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=GO_TO_ERROR_PAGE");
        }


    }
}
