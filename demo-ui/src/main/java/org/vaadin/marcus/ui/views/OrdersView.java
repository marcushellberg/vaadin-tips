package org.vaadin.marcus.ui.views;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.marcus.service.OrderService;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.ui.components.OrdersGrid;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.util.ViewConfig;

import java.util.List;

@ViewConfig(uri = "orders", displayName = "Orders")
public class OrdersView extends VerticalLayout implements View {
    private OrderService orderService = OrderService.get();
    private OrdersGrid ordersTable;

    public OrdersView() {

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        createLayout();
    }

    private void createLayout() {
        Label header = new Label("Orders, synchronous");
        ordersTable = new OrdersGrid();
        addComponents(header, ordersTable);

        header.addStyleName(MyTheme.LABEL_H1);
        ordersTable.setSizeFull();
        setExpandRatio(ordersTable, 1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        fetchOrders();
    }

    private void fetchOrders() {
        showOrders(orderService.getOrders());
    }

    public void showOrders(List<Order> orders) {
        ordersTable.setContainer(new BeanItemContainer<>(Order.class, orders));
    }

}
