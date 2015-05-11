package org.vaadin.marcus.ui.views.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.vaadin.marcus.entity.User;
import org.vaadin.marcus.service.LoginService;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.event.EventBus;

import javax.security.auth.login.LoginException;

public class LoginBox extends VerticalLayout {

    private LoginService loginService = new LoginService();
    private TextField username;
    private PasswordField password;

    public LoginBox() {
        setWidth("400px");
        addStyleName(MyTheme.LOGIN_BOX);
        setSpacing(true);
        setMargin(true);

        addCaption();
        addForm();
        addButtons();
    }

    private void addCaption() {
        Label caption = new Label("Login to system");
        addComponent(caption);

        caption.addStyleName(MyTheme.LABEL_H1);
    }

    private void addForm() {
        FormLayout loginForm = new FormLayout();
        username = new TextField("Username");
        password = new PasswordField("Password");
        loginForm.addComponents(username, password);
        addComponent(loginForm);

        loginForm.setSpacing(true);
        loginForm.forEach(component -> component.setWidth("100%"));

        username.focus();
    }

    private void addButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button forgotButton = new Button("Forgot", click -> Notification.show("Not implemented", Notification.Type.TRAY_NOTIFICATION));
        Button loginButton = new Button("Login", click -> login());
        buttonsLayout.addComponents(forgotButton, loginButton);
        addComponent(buttonsLayout);

        buttonsLayout.setSpacing(true);

        forgotButton.addStyleName(MyTheme.BUTTON_LINK);

        loginButton.addStyleName(MyTheme.BUTTON_PRIMARY);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
    }

    private void login() {
        try {
            User user = loginService.login(username.getValue(), password.getValue());
            EventBus.post(new LoginEvent(user));
        } catch (LoginException e) {
            Notification.show("Login failed.", "Hint: use any non-empty strings", Notification.Type.WARNING_MESSAGE);
            username.focus();
        }
    }
}
