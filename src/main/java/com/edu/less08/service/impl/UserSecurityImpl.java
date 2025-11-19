package com.edu.less08.service.impl;

import com.edu.less08.dao.*;
import com.edu.less08.dao.impl.RoleUtil;
import com.edu.less08.dao.impl.StatusUtil;
import com.edu.less08.model.*;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.UserSecurityService;
import com.edu.less08.service.util.PasswordSecurityUtil;
import com.edu.less08.service.util.RegistrationInfoValidator;

import java.util.Optional;


public class UserSecurityImpl implements UserSecurityService {
    private final PasswordSecurityUtil passwordSecurityUtil = new PasswordSecurityUtil();
    private final RegistrationInfoValidator registrationInfoValidator = new RegistrationInfoValidator();
    private final UserDao userDao = DaoProvider.getInstance().getUserDao();


    @Override
    public UserView authenticate(String userLogin, String password) throws ServiceException {
        try {
            Optional<User> userOptional = userDao.getUserByLogin(userLogin);
            if (userOptional.isEmpty()) {
                throw new ServiceException().setUserMessage("Login is not exist");
            }
            User user = userOptional.get();
            if (!passwordSecurityUtil.checkPassword(password, user.getPassword())) {
                throw new ServiceException().setUserMessage("Password is not correct");
            }
            UserView userView = convertToUserView(user);
            return userView;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registrate(RegistrationInfo registrationInfo) throws ServiceException {
        try {
            registrationInfoValidator.validate(registrationInfo);
            User user = createUserFromRegistrationInfo(registrationInfo);
            userDao.addUser(user);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private UserView convertToUserView(User user) throws ServiceException {
        try {
            int userId = user.getId();
            String userLogin = user.getLogin();
            String userEmail = user.getEmail();
            int userRoleId = user.getRoleId();
            String userRole = RoleUtil.getRoleNameById(userRoleId).name();
            UserView userView = new UserView(userId, userLogin, userEmail, userRole);
            return userView;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private User createUserFromRegistrationInfo(RegistrationInfo regInfo) throws DaoException {
        User user = new User();
        user.setLogin(regInfo.getLogin());
        user.setEmail(regInfo.getEmail());
        user.setPassword(passwordSecurityUtil.hashPassword(regInfo.getPassword()));
        user.setRoleId(RoleUtil.getRoleIdByName(UserRole.USER));
        user.setStatusId(StatusUtil.getStatusIdByName(Status.ACTIVE));
        return user;
    }
}
