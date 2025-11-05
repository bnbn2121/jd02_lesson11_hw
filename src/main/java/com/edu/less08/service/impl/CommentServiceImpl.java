package com.edu.less08.service.impl;

import com.edu.less08.dao.CommentDao;
import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.UserDao;
import com.edu.less08.model.Comment;
import com.edu.less08.model.User;
import com.edu.less08.service.CommentService;
import com.edu.less08.service.ServiceException;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    CommentDao commentDao = DaoProvider.getInstance().getCommentDao();
    UserDao userDao = DaoProvider.getInstance().getUserDao();

    @Override
    public Comment addNewsComment(int newsId, String commentText, int authorId) throws ServiceException {
        try {
            Comment comment = new Comment();
            comment.setText(commentText);
            User author = new User();
            author.setId(authorId);
            comment.setAuthor(author);
            return commentDao.addNewsComment(comment, newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteComment(int commentId) throws ServiceException {
        try {
            commentDao.deleteComment(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> getCommentsByNewsId(int newsId) throws ServiceException {
        try {
            return commentDao.getAllCommentsByNews(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
