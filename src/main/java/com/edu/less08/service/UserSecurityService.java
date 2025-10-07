package com.edu.less08.service;

import com.edu.less08.model.RegistrationInfo;
import com.edu.less08.model.UserView;

public interface UserSecurityService {
    UserView authenticate(String userLogin, String password) throws ServiceException;
    boolean registrate(RegistrationInfo registrationInfo) throws ServiceException;
}
