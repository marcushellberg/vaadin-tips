package org.vaadin.marcus.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(uri = "layouts", displayName = "Layouts")
public class LayoutView extends VerticalLayout implements View {

    public LayoutView() {
        addStyleName(MyTheme.LAYOUT_VIEW);
        setMargin(true);
        setSizeFull();

        createLayout();
    }

    private void createLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        Label label = new Label("Hello");
        Button button = new Button("World");
        layout.addComponents(label, button);
        addComponent(layout);

//        label.setWidth("80%");
//        button.setWidth("20%");

//        layout.setWidth("100%");

        // Visualize the layout size and slot sizes. (Please don't ever use this to set styles in a real application)
//        Page.getCurrent().getStyles().add(".layout-view .v-horizontallayout {border: 1px solid blue; padding-right: 4px;} .layout-view .v-horizontallayout .v-slot {border: 1px solid red;}");

//        layout.setExpandRatio(label, 1.0f);
//        button.setSizeUndefined();


//        layout.setExpandRatio(label, 0.8f);
//        layout.setExpandRatio(button, 0.2f);
//        button.setWidth("100%");

//        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
//        label.setSizeUndefined();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
