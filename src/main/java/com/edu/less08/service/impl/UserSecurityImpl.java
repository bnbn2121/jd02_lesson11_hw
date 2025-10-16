package com.edu.less08.service.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.RoleDao;
import com.edu.less08.dao.UserDao;
import com.edu.less08.model.RegistrationInfo;
import com.edu.less08.model.User;
import com.edu.less08.model.UserRole;
import com.edu.less08.model.UserView;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.UserSecurityService;
import com.edu.less08.service.util.PasswordHasher;


public class UserSecurityImpl implements UserSecurityService {
    private final PasswordHasher passwordHasher = new PasswordHasher();
    private final UserDao userDao = DaoProvider.getInstance().getUserDao();
    private final RoleDao roleDAO = DaoProvider.getInstance().getRoleDao();


    @Override
    public UserView authenticate(String userLogin, String password) throws ServiceException {
        User user = null;
        try {
            user = userDao.getUserByLogin(userLogin);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        if (user == null) {
            throw new ServiceException().setUserMessage("Login is not exist");
        }

        if (!passwordHasher.checkPassword(password, user.getPassword())) {
            throw new ServiceException().setUserMessage("Password is not correct");
        }

        UserView userView = getUserView(user);
        return userView;
    }

    private UserView getUserView(User user) throws ServiceException{
        try {
            String userLogin = user.getLogin();
            String userEmail = user.getEmail();
            int userRoleId = user.getRoleId();
            String userRole = roleDAO.getRoleNameById(userRoleId).name();
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
                roleDAO.getRoleIdByName(UserRole.USER),
                1
        );

            userDao.addUser(user);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
