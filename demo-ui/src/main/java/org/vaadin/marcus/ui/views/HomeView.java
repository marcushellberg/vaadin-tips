package org.vaadin.marcus.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import org.vaadin.marcus.ui.components.VerticalSpacedLayout;
import org.vaadin.marcus.util.CurrentUser;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(uri = "", displayName = "Home")
public class HomeView extends VerticalSpacedLayout implements View {

    public HomeView() {
        Label caption = new Label("Welcome, " + CurrentUser.get().getUsername());
        Label description = new Label(
                "This project contains a collection of tips and tricks that will hopefully make it easier and more fun for you to work with Vaadin. Please read the readme file at <a href='https://github.com/vaadin-marcus/vaadin-tips'>https://github.com/vaadin-marcus/vaadin-tips</a>.", ContentMode.HTML);

        addComponents(caption, description);

        caption.addStyleName(MyTheme.LABEL_HUGE);
        description.addStyleName(MyTheme.LABEL_LARGE);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
