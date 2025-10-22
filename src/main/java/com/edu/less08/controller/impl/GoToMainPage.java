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
import java.util.List;

public class GoToMainPage implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
    private int newsPerPage = 3;
    private int totalPages;
    private int currentPage;
    private int prevPage;
    private int nextPage;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getCurrentPage(request);
            calcTotalPages(request, response);
            List<News> listNews = loadListNews();
            setPaginationLinks();

            request.setAttribute("listNews", listNews);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("prevPage", prevPage);
            request.setAttribute("nextPage", nextPage);
            request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
        } catch (ServiceException e) {
            //обработать нормально
        }
    }

    private void getCurrentPage(HttpServletRequest request){
        String viewPageParameter = request.getParameter("viewPage");
        if (viewPageParameter==null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(viewPageParameter);
        }
    }

    private void calcTotalPages(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        int totalActiveNews = newsService.getAllActiveNewsCount();
        if (totalActiveNews == 0) {
            request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
        }
        totalPages = (int) Math.ceil(1.0 * totalActiveNews / newsPerPage);
    }

    private List<News> loadListNews() throws ServiceException {
        int indexFirstNewsForLoad = (currentPage - 1) * newsPerPage;
        return newsService.getNews(indexFirstNewsForLoad, newsPerPage);
    }

    private void setPaginationLinks() {
        prevPage = currentPage - 1;
        nextPage = currentPage + 1;
        if (prevPage == 0) {
            prevPage = 1;
        }
        if (nextPage > totalPages) {
            nextPage = totalPages;
        }
    }
}
