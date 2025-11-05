package com.edu.less08.dao;

import com.edu.less08.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Comment addNewsComment(Comment comment, int newsId) throws DaoException;
    Comment addUserComment(Comment comment, int userId) throws DaoException;
    Optional<Comment> getCommentById(int commentId) throws DaoException;
    List<Comment> getAllCommentsByNews(int newsId) throws DaoException;
    List<Comment> getAllCommentsByUser(int userId) throws DaoException;
    void deleteComment(int commentId) throws DaoException;
}
