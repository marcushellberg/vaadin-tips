package org.vaadin.marcus.util;

import com.vaadin.server.VaadinSession;
import org.vaadin.marcus.entity.User;

/**
 * Convenience wrapper for storing and retreiving a user from the VaadinSession
 */
public class CurrentUser {

    private static final String KEY = "currentser";

    public static void set(User user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
    }

    public static User get() {
        return (User) VaadinSession.getCurrent().getAttribute(KEY);
    }

    public static boolean isLoggedIn() {
        return get() != null;
    }
}
