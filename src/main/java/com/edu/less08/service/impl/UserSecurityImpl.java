package com.edu.less08.service.impl;

import com.edu.less08.dao.*;
import com.edu.less08.dao.impl.RoleUtil;
import com.edu.less08.dao.impl.StatusUtil;
import com.edu.less08.model.*;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.UserSecurityService;
import com.edu.less08.service.util.PasswordHasher;
import com.edu.less08.service.util.RegistrationInfoValidator;

import java.util.Optional;


public class UserSecurityImpl implements UserSecurityService {
    private final PasswordHasher passwordHasher = new PasswordHasher();
    private final RegistrationInfoValidator registrationInfoValidator = new RegistrationInfoValidator();
    private final UserDao userDao = DaoProvider.getInstance().getUserDao();
    private final int defaultUserRoleId;
    private final int defaultUserStatusId;

    {
        try {
            defaultUserRoleId = RoleUtil.getRoleIdByName(UserRole.USER);
            defaultUserStatusId = StatusUtil.getStatusIdByName(Status.ACTIVE);
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
            UserView userView = convertToUserView(user);
            return userView;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private UserView convertToUserView(User user) throws ServiceException{
        try {
            String userLogin = user.getLogin();
            String userEmail = user.getEmail();
            int userRoleId = user.getRoleId();
            String userRole = RoleUtil.getRoleNameById(userRoleId).name();
            UserView userView = new UserView(userLogin, userEmail, userRole);
            return userView;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean registrate(RegistrationInfo registrationInfo) throws ServiceException{
        try {
        registrationInfoValidator.validate(registrationInfo);
        User user = new User(
                0,
                registrationInfo.getLogin(),
                registrationInfo.getEmail(),
                passwordHasher.hashPassword(registrationInfo.getPassword()),
                defaultUserRoleId,
                defaultUserStatusId
        );

            userDao.addUser(user);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
