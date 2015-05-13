package org.vaadin.marcus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.vaadin.marcus.ui.AwesomeApp;
import org.vaadin.marcus.ui.views.login.LoginEvent;
import org.vaadin.marcus.ui.views.login.LoginView;
import org.vaadin.marcus.util.CurrentUser;
import org.vaadin.marcus.util.event.LogoutEvent;
import org.vaadin.marcus.util.event.NavigationEvent;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@Title("AwesomeApp")
@Widgetset("org.vaadin.marcus.MyAppWidgetset")
public class DemoUI extends UI {
    private EventBus eventBus;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupEventBus();

        if (CurrentUser.isLoggedIn()) {
            setContent(new AwesomeApp());
        } else {
            setContent(new LoginView());
        }
    }

    private void setupEventBus() {
        eventBus = new EventBus((throwable, subscriberExceptionContext) -> {
            // log error
            throwable.printStackTrace();
        });
        eventBus.register(this);
    }

    @Subscribe
    public void userLoggedIn(LoginEvent event) {
        CurrentUser.set(event.getUser());
        setContent(new AwesomeApp());
    }

    @Subscribe
    public void navigateTo(NavigationEvent view) {
        getNavigator().navigateTo(view.getViewName());
    }

    @Subscribe
    public void logout(LogoutEvent logoutEvent) {
        // Don't invalidate the underlying HTTP session if you are using it for something else
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();

    }

    public static DemoUI getCurrent() {
        return (DemoUI) UI.getCurrent();
    }

    public static EventBus getEventBus() {
        return getCurrent().eventBus;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = DemoUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
