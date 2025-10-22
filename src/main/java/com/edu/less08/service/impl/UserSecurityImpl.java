package com.edu.less08.service.impl;

import com.edu.less08.dao.*;
import com.edu.less08.model.*;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.UserSecurityService;
import com.edu.less08.service.util.PasswordHasher;

import java.util.Optional;


public class UserSecurityImpl implements UserSecurityService {
    private final PasswordHasher passwordHasher = new PasswordHasher();
    private final UserDao userDao = DaoProvider.getInstance().getUserDao();
    private final RoleDao roleDao = DaoProvider.getInstance().getRoleDao();
    private final StatusDao statusDao = DaoProvider.getInstance().getStatusDao();
    private final int defaultUserRoleId;
    private final int defaultUserStatusId;

    {
        try {
            defaultUserRoleId = roleDao.getRoleIdByName(UserRole.USER);
            defaultUserStatusId = statusDao.getStatusIdByName(Status.ACTIVE);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public UserView authenticate(String userLogin, String password) throws ServiceException {
        try {
            Optional<User> userOptional = userDao.getUserByLogin(userLogin);
            if (userOptional.isEmpty()) {
                throw new ServiceException().setUserMessage("Login is not exist");
            }
            User user = userOptional.get();
            if (!passwordHasher.checkPassword(password, user.getPassword())) {
                throw new ServiceException().setUserMessage("Password is not correct");
            }
            UserView userView = getUserView(user);
            return userView;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private UserView getUserView(User user) throws ServiceException{
        try {
            String userLogin = user.getLogin();
            String userEmail = user.getEmail();
            int userRoleId = user.getRoleId();
            String userRole = roleDao.getRoleNameById(userRoleId).name();
            UserView userView = new UserView(userLogin, userEmail, userRole);
            return userView;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void validate(RegistrationInfo registrationInfo) throws ServiceException {
        try {
            if(registrationInfo.getTermsAgreement() == null) {
                throw new ServiceException().setUserMessage("Terms was not accepted");
            }

            String userLogin = registrationInfo.getLogin();
            if (userDao.getUserByLogin(userLogin) != null) {
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

    @Override
    public boolean registrate(RegistrationInfo registrationInfo) throws ServiceException{
        try {
        validate(registrationInfo);
        User user = new User(
                0,
                registrationInfo.getLogin(),
                registrationInfo.getEmail(),
                passwordHasher.hashPassword(registrationInfo.getPassword()),
                roleDao.getRoleIdByName(UserRole.USER),
                statusDao.getStatusIdByName(Status.ACTIVE)
        );

            userDao.addUser(user);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
