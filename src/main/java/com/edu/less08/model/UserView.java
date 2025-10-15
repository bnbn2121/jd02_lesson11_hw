package com.edu.less08.model;

import java.io.Serializable;
import java.util.Objects;

public class UserView implements Serializable {
    private String login;
    private String email;
    private String role;

    public UserView(String login, String email, String role) {
        this.login = login;
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserView userView = (UserView) o;
        return Objects.equals(login, userView.login) && Objects.equals(email, userView.email) && Objects.equals(role, userView.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, role);
    }

    @Override
    public String toString() {
        return "UserView{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
