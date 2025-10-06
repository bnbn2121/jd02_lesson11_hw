package com.edu.less08.service;

import com.edu.less08.model.RegistrationInfo;
import com.edu.less08.model.UserSession;

public interface UserSecurityService {
    UserSession authenticate(String userLogin, String password) throws ServiceException;
    boolean registrate(RegistrationInfo registrationInfo) throws ServiceException;
}
