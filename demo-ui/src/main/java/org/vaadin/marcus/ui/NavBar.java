package org.vaadin.marcus.ui;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.event.EventBus;
import org.vaadin.marcus.util.event.LogoutEvent;
import org.vaadin.marcus.util.event.NavigationEvent;

import java.util.HashMap;
import java.util.Map;

public class NavBar extends CssLayout implements ViewChangeListener {

    private Map<String, Button> buttonMap = new HashMap<>();

    public NavBar() {
        setHeight("100%");
        addStyleName(MyTheme.MENU_ROOT);
        addStyleName(MyTheme.NAVBAR);

        Label logo = new Label("<strong>Awesome</strong>App", ContentMode.HTML);
        logo.addStyleName(MyTheme.MENU_TITLE);
        addComponent(logo);

        addLogoutButton();
    }

    private void addLogoutButton() {
        Button logout = new Button("Log out", click -> EventBus.post(new LogoutEvent()));
        addComponent(logout);

        logout.addStyleName(MyTheme.BUTTON_LOGOUT);
        logout.addStyleName(MyTheme.BUTTON_BORDERLESS);
        logout.setIcon(FontAwesome.SIGN_OUT);
    }

    public void addView(String uri, String displayName) {
        Button viewButton = new Button(displayName,
                click -> EventBus.post(new NavigationEvent(uri)));
        viewButton.addStyleName(MyTheme.MENU_ITEM);
        viewButton.addStyleName(MyTheme.BUTTON_BORDERLESS);
        buttonMap.put(uri, viewButton);

        addComponent(viewButton, components.size() - 1);
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true; // false blocks navigation, always return true here
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        buttonMap.values().forEach(button -> button.removeStyleName(MyTheme.SELECTED));
        Button button = buttonMap.get(event.getViewName());
        if (button != null) button.addStyleName(MyTheme.SELECTED);
    }
}
