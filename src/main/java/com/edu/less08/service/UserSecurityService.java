package com.edu.less08.service;

import com.edu.less08.model.User;

public interface UserSecurityService {
    User authenticate(String userLogin, String password) throws ServiceException;
}
