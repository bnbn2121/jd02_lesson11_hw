package com.edu.less08.service;

import com.edu.less08.model.Comment;
import com.edu.less08.model.UserView;

import java.util.List;

public interface CommentService {
    Comment addNewsComment(int newsId, String commentText, int authorId) throws ServiceException;
    void deleteComment(int commentId) throws ServiceException;
    List<Comment> getCommentsByNewsId(int newsId) throws ServiceException;
}
