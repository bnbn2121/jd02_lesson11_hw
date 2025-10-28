package com.edu.less08.service.util;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.UserDao;
import com.edu.less08.model.RegistrationInfo;
import com.edu.less08.service.ServiceException;

import java.util.Optional;

public class RegistrationInfoValidator {
    private UserDao userDao = DaoProvider.getInstance().getUserDao();

    public void validate(RegistrationInfo registrationInfo) throws ServiceException {
        try {
            if(registrationInfo.getTermsAgreement() == null) {
                throw new ServiceException().setUserMessage("Terms was not accepted");
            }

            String userLogin = registrationInfo.getLogin();
            if (userDao.getUserByLogin(userLogin).isPresent()) {
                throw new ServiceException().setUserMessage("Login was already used");
            }

            String password = registrationInfo.getPassword();
            String confirmPassword = registrationInfo.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                throw new ServiceException().setUserMessage("Passwords aren't match");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
