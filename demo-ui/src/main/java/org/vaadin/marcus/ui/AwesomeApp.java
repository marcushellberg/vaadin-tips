package org.vaadin.marcus.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.vaadin.marcus.DemoUI;
import org.vaadin.marcus.ui.views.*;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.PageTitleUpdater;
import org.vaadin.marcus.util.LazyProvider;
import org.vaadin.marcus.util.ViewConfig;
import org.vaadin.marcus.ui.views.AsyncOrdersView;
import org.vaadin.marcus.ui.views.form.FormView;
import org.vaadin.marcus.ui.views.OrdersView;
import org.vaadin.marcus.ui.views.render.SlowRenderingView;

public class AwesomeApp extends HorizontalLayout {

    private NavBar navBar;
    private Panel content;
    private Navigator navigator;

    public AwesomeApp() {
        addStyleName(MyTheme.MAIN_LAYOUT);

        setSizeFull();

        initLayouts();
        setupNavigator();
    }

    private void initLayouts() {
        navBar = new NavBar();
        // Use panel as main content container to allow it's content to scroll
        content = new Panel();
        content.setSizeFull();
        content.addStyleName(MyTheme.PANEL_BORDERLESS);

        addComponents(navBar, content);
        setExpandRatio(content, 1);
    }

    private void setupNavigator() {
        navigator = new Navigator(DemoUI.getCurrent(), content);

        registerViews();

        // Add view change listeners so we can do things like select the correct menu item and update the page title
        navigator.addViewChangeListener(navBar);
        navigator.addViewChangeListener(new PageTitleUpdater());

        navigator.navigateTo(navigator.getState());
    }

    private void registerViews() {
        addView(HomeView.class);
        addView(OrdersView.class);
        addView(AsyncOrdersView.class);
        addView(LazyOrdersView.class);
        addView(FormView.class);
        addView(SlowRenderingView.class);
        addView(HeapDestroyerView.class);
        navigator.setErrorView(ErrorView.class);
    }

    /**
     * Registers av given view to the navigator and adds it to the NavBar
     */
    private void addView(Class<? extends View> viewClass) {
        ViewConfig viewConfig = viewClass.getAnnotation(ViewConfig.class);

        switch (viewConfig.createMode()) {
            case ALWAYS_NEW:
                navigator.addView(viewConfig.uri(), viewClass);
                break;
            case LAZY_INIT:
                navigator.addProvider(new LazyProvider(viewConfig.uri(), viewClass));
                break;
            case EAGER_INIT:
                try {
                    navigator.addView(viewConfig.uri(), viewClass.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        navBar.addView(viewConfig.uri(), viewConfig.displayName());
    }
}
