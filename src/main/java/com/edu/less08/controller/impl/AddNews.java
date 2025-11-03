package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.model.News;
import com.edu.less08.model.UserView;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddNews implements Command {
    private NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String brief = request.getParameter("brief");
        String content = request.getParameter("content");
        String imagePath = request.getParameter("imagePath");
        UserView publisher = (UserView) request.getSession().getAttribute("user");
        try {
            News news = newsService.addNews(title, brief, content, imagePath, publisher.getId());
            response.sendRedirect("Controller?command=go_to_main_page");
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=GO_TO_ERROR_PAGE");
        }


    }
}
