package org.vaadin.marcus.ui.views.login;

import org.vaadin.marcus.entity.User;

public class LoginEvent {
    private User user;

    public LoginEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
