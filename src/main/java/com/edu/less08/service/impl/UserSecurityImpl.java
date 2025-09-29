package com.edu.less08.service.impl;

import com.edu.less08.model.User;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.UserSecurityService;

import java.util.*;

public class UserSecurityImpl implements UserSecurityService {
    private List<User> registeredUsers = new ArrayList<>();
    private Map<String, String> userPasswords = new HashMap<>();

    {
        registeredUsers.add(new User("aaa", "aaa@mail.ru"));
        registeredUsers.add(new User("bbb", "bbb@mail.ru"));
        registeredUsers.add(new User("ccc", "ccc@mail.ru"));
        registeredUsers.forEach(user -> userPasswords.put(user.getLogin(), user.getLogin()));
    }

    private Optional<User> findUserByLogin(String inputLogin) {
        return registeredUsers.stream()
                .filter(user1 -> user1.getLogin().equals(inputLogin))
                .findFirst();
    }

    public User authenticate(String userLogin, String password) throws ServiceException {
        User user = findUserByLogin(userLogin).orElseThrow(() -> new ServiceException().setUserMessage("Login not found"));

        if (userPasswords.get(userLogin).equals(password)) {
            return user;
        } else {
            throw new ServiceException().setUserMessage("Password is not correct");
        }
    }
}
