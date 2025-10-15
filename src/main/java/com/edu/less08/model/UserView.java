package com.edu.less08.model;

import java.io.Serializable;
import java.util.Objects;

public class UserView implements Serializable {
    private String login;
    private String email;
    private int roleId;

    public UserView(String login, String email, int roleId) {
        this.login = login;
        this.email = email;
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserView userView = (UserView) o;
        return roleId == userView.roleId && Objects.equals(login, userView.login) && Objects.equals(email, userView.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, roleId);
    }

    @Override
    public String toString() {
        return "UserView{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
