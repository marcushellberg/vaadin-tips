package org.vaadin.marcus.ui.views;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import org.vaadin.marcus.ui.components.OrdersGrid;
import org.vaadin.marcus.ui.components.VerticalSpacedLayout;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.service.OrderService;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.ViewConfig;

import java.util.List;

@ViewConfig(uri = "async", displayName = "Orders (async)")
public class AsyncOrdersView extends VerticalSpacedLayout implements View {

    private OrderService orderService = OrderService.get();
    private OrdersGrid ordersTable;
    private ProgressBar progressBar;
    private HorizontalLayout loadingNotificaton;

    public AsyncOrdersView() {
        setSizeFull();

        createLayout();
    }

    private void createLayout() {
        Label header = new Label("Orders, asynchronous");
        ordersTable = new OrdersGrid();
        addComponent(header);
        addLoadingNotification();

        header.addStyleName(MyTheme.LABEL_H1);

        ordersTable.setSizeFull();


    }

    private void addLoadingNotification() {
        progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        loadingNotificaton = new HorizontalLayout();
        loadingNotificaton.setSpacing(true);
        loadingNotificaton.addComponents(progressBar, new Label("Hang on, fetching orders..."));
        addComponents(loadingNotificaton);
        setComponentAlignment(loadingNotificaton, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        fetchOrders();
    }

    protected void fetchOrders() {
        getUI().setPollInterval(200);
        // Do not block on call. Instead we use a future that notifies us when it's done.
        // Note that we need to have either Push or polling in use to be able to initiate changes from the server.
        Futures.addCallback(orderService.getOrdersAsync(), new FutureCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> orders) {
                showOrders(orders);
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Do reasonable things to handle failure
            }
        });
    }

    protected void showOrders(List<Order> orders) {

        //Note getUI() vs UI.getCurrent(). The latter is not accessible from the background thread.
        getUI().access(() -> {
            ordersTable.setContainer(new BeanItemContainer<>(Order.class, orders));
            replaceComponent(loadingNotificaton, ordersTable);
            setExpandRatio(ordersTable, 1);
            getUI().setPollInterval(-1);
        });

    }


}
