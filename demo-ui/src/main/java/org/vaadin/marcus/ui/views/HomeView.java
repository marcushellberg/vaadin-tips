package org.vaadin.marcus.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import org.vaadin.marcus.components.VerticalSpacedLayout;
import org.vaadin.marcus.util.CurrentUser;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(uri="", displayName = "Home")
public class HomeView extends VerticalSpacedLayout implements View {

    public HomeView() {
        Label caption = new Label("Welcome, " + CurrentUser.get().getUsername());
        addComponent(caption);


        caption.addStyleName(MyTheme.LABEL_HUGE);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
