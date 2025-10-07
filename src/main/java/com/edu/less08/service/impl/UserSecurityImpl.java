package com.edu.less08.service.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.UserDao;
import com.edu.less08.model.RegistrationInfo;
import com.edu.less08.model.User;
import com.edu.less08.model.UserSession;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.UserSecurityService;

import java.util.*;


public class UserSecurityImpl implements UserSecurityService {
    private UserDao userDao = DaoProvider.getInstance().getUserDao();
    private List<UserSession> registeredUsers = new ArrayList<>();
    private Map<String, String> userPasswords = new HashMap<>();

    {
        registeredUsers.add(new UserSession("aaa", "aaa@mail.ru"));
        registeredUsers.add(new UserSession("bbb", "bbb@mail.ru"));
        registeredUsers.add(new UserSession("ccc", "ccc@mail.ru"));
        registeredUsers.forEach(user -> userPasswords.put(user.getLogin(), user.getLogin()));
    }

    private Optional<UserSession> findUserByLogin(String inputLogin) {
        return registeredUsers.stream()
                .filter(user1 -> user1.getLogin().equals(inputLogin))
                .findFirst();
    }

    @Override
    public UserSession authenticate(String userLogin, String password) throws ServiceException {
        UserSession user = findUserByLogin(userLogin).orElseThrow(() -> new ServiceException().setUserMessage("Login not found"));

        if (userPasswords.get(userLogin).equals(password)) {
            return user;
        } else {
            throw new ServiceException().setUserMessage("Password is not correct");
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
                throw new ServiceException().setUserMessage("Passwords don't match");
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
                registrationInfo.getPassword(),
                1,
                1
        );

            userDao.addUser(user);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
