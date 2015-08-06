package org.vaadin.marcus.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(uri="layouts", displayName = "Layouts")
public class LayoutView extends VerticalLayout implements View {

    public LayoutView() {
        addStyleName(MyTheme.LAYOUT_VIEW);
        setMargin(true);
        setSizeFull();

        // Please don't ever use this to set styles in a real application
        // Here I just wanted to keep the CSS visible in the example
        Page.getCurrent()
                .getStyles()
                .add(
                        ".layout-view .v-horizontallayout {border: 1px solid blue; padding-right: 4px;}" +
                        ".layout-view .v-horizontallayout .v-slot {border: 1px solid red;}"
                );

        createLayout();
    }

    private void createLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        Label label = new Label("Hello");
        Button button = new Button("World");
        layout.addComponents(label, button);
        addComponent(layout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
