package org.vaadin.marcus.ui.views.render;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import org.vaadin.marcus.components.VerticalSpacedLayout;
import org.vaadin.marcus.service.OrderService;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(uri="slow-render", displayName = "Slow rendering view")
public class SlowRenderingView extends VerticalSpacedLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        OrderService.get()
                .fetchOrders(0, 200)
                .forEach(order -> addComponent(new OrderLayout(order)));
    }
}