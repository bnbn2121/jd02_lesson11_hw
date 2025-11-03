package com.edu.less08.model;

import java.io.Serializable;
import java.util.Objects;

public class UserView implements Serializable {
    private int id;
    private String login;
    private String email;
    private String role;

    public UserView(int id, String login, String email, String role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
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
    public String toString() {
        return "UserView{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
