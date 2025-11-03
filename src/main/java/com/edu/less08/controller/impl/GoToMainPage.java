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
import java.util.List;

public class GoToMainPage implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
    private static final int NEWS_PER_PAGE_DEFAULT = 3;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int currentPage = getCurrentPage(request);
            int newsPerPage = getCountNewsPerPage(request);
            int totalPages = calcTotalPages(newsPerPage);
            if (totalPages == 0) {
                request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
                return;
            }
            if (currentPage > totalPages) {
                currentPage = 1;
            }
            List<News> listNews = loadListNews(currentPage, newsPerPage);
            request.setAttribute("listNews", listNews);

            setPaginationLinks(request, currentPage, totalPages);
            request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=GO_TO_ERROR_PAGE");
        }
    }

    private int getCurrentPage(HttpServletRequest request) {
        String viewPageParameter = request.getParameter("currentPage");
        if (viewPageParameter == null || viewPageParameter.isEmpty()) {
            return 1;
        } else {
            return Integer.parseInt(viewPageParameter);
        }
    }

    private int calcTotalPages(int newsPerPage) throws ServiceException {
        int totalActiveNews = newsService.getAllActiveNewsCount();
        if (totalActiveNews == 0) {
            return 0;
        }
        return (int) Math.ceil(1.0 * totalActiveNews / newsPerPage);
    }

    private List<News> loadListNews(int currentPage, int newsPerPage) throws ServiceException {
        int indexFirstNewsForLoad = (currentPage - 1) * newsPerPage;
        return newsService.getNews(indexFirstNewsForLoad, newsPerPage);
    }

    private void setPaginationLinks(HttpServletRequest request, int currentPage, int totalPages) {
        int prevPage = currentPage - 1;
        int nextPage = currentPage + 1;
        if (prevPage == 0) {
            prevPage = 1;
        }
        if (nextPage > totalPages) {
            nextPage = totalPages;
        }
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("prevPage", prevPage);
        request.setAttribute("nextPage", nextPage);

    }

    private int getCountNewsPerPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int newsPerPage;
        if (session.getAttribute("newsPerPage") == null) {
            newsPerPage = NEWS_PER_PAGE_DEFAULT;
        } else {
            newsPerPage = (int) request.getSession().getAttribute("newsPerPage");
        }
        return newsPerPage;
    }
}
