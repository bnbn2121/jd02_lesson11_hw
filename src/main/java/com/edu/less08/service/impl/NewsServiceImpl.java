package com.edu.less08.service.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.NewsContextStorageDao;
import com.edu.less08.dao.NewsDao;
import com.edu.less08.dao.pool.ConnectionManager;
import com.edu.less08.model.News;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private final NewsDao newsDao = DaoProvider.getInstance().getNewsDao();
    private final NewsContextStorageDao newsContextStorageDao = DaoProvider.getInstance().getNewsContextStorageDao();

    public static void main(String[] args) throws ServiceException, ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://127.0.0.1/news_app_database?sslMode=DISABLED&allowPublicKeyRetrieval=true";
        String daoUser = "root";
        String daoPassword = "123";
        int maxPoolSize = 3;
        Class.forName("com.mysql.cj.jdbc.Driver");
        ConnectionManager.initConnectionPool(url, daoUser, daoPassword, maxPoolSize);

        new NewsServiceImpl().addNews("название", "кратное описание", "текст новости полный\nв две строки%nили в три", "https://media.lpgenerator.ru/uploads/2019/07/11/13.jpg");
    }

    private News createNewsPrototype(String title, String brief, String imagePath) throws ServiceException {
        News.NewsBuilder builder = new News.NewsBuilder();
        News prototypeNews = builder
                .setTitle(title)
                .setBrief(brief)
                .setImagePath(imagePath)
                .setContentPath("not init yet")
                .setPublishDate(LocalDate.now())
                .setPublisherId(4)
                .setStatusId(1)
                .build();
        return prototypeNews;
    }

    @Override
    public List<News> getNews(int indexFirst, int countNews) throws ServiceException {
        try {
            return newsDao.getNewsPaginated(indexFirst, countNews);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News addNews(String title, String brief, String content, String imagePath) throws ServiceException {
        try {
            News news = createNewsPrototype(title, brief, imagePath);
            news = newsDao.addNews(news);
            int newsId = news.getId();
            String contentPath = newsContextStorageDao.addContext(newsId, content);
            news.setContentPath(contentPath);
            newsDao.updateNews(news);
            return news;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
