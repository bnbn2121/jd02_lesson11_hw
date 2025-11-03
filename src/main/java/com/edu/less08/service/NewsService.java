package com.edu.less08.service;

import com.edu.less08.model.News;
import com.edu.less08.model.Status;

import java.util.List;

public interface NewsService {
    List<News> getNews(int indexFirst, int countNews) throws ServiceException;
    News addNews(String title, String brief, String content, String imagePath, int publisherId) throws ServiceException;
    int getAllActiveNewsCount() throws ServiceException;
    News getNewsById(int id) throws ServiceException;
    News updateNews(int newsId, String newTitle, String newBrief, String newContent, String newImagePath, Status status) throws ServiceException;
}
