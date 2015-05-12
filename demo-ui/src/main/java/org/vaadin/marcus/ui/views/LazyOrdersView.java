package org.vaadin.marcus.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.ui.components.OrdersGrid;
import org.vaadin.marcus.service.OrderService;
import org.vaadin.marcus.util.ViewConfig;
import org.vaadin.viritin.LazyList;
import org.vaadin.viritin.ListContainer;

@ViewConfig(uri = "lazy-orders", displayName = "Orders (lazy)")
public class LazyOrdersView extends VerticalLayout implements View {

    OrderService orderService = OrderService.get();
    private OrdersGrid ordersTable;

    public LazyOrdersView() {

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        createLayout();
    }

    private void createLayout() {
        Label header = new Label("Orders, lazy loaded");
        ordersTable = new OrdersGrid();
        header.addStyleName(MyTheme.LABEL_H1);
        addComponents(header, ordersTable);

        ordersTable.setSizeFull();
        setExpandRatio(ordersTable, 1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Instead of fetching any orders upfront, just tell the container where it can find data when it needs some
        ordersTable.setContainer(new ListContainer<>(new LazyList<>(orderService::fetchOrders, orderService::getOrderCount)));
    }


}
