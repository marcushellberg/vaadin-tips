package org.vaadin.marcus.service;

import org.vaadin.marcus.entity.User;

import javax.security.auth.login.LoginException;

public class LoginService {

    public User login(String username, String password) throws LoginException {
        if (!username.isEmpty() && !password.isEmpty()) {
            return new User(username);
        }
        throw new LoginException();
    }
}
