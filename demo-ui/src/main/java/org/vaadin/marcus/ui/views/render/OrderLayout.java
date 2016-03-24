package org.vaadin.marcus.ui.views.render;

import com.vaadin.ui.*;
import org.joda.time.format.DateTimeFormat;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.util.MyTheme;

import java.text.NumberFormat;


public class OrderLayout extends CustomComponent {

    public OrderLayout(Order order) {
        Panel panel = new Panel();
        HorizontalLayout layout = new HorizontalLayout();
        panel.setContent(layout);
        setCompositionRoot(panel);
        
        Label orderTime = new Label();
        VerticalLayout lineItemsLayout = new VerticalLayout();
        Label lineItemsCaption = new Label("Line items:");
        Label orderTotal = new Label("");

        lineItemsLayout.addComponent(lineItemsCaption);
        layout.addComponents(orderTime, lineItemsLayout, orderTotal);

        panel.setCaption("Order " + order.getId());

        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setWidth("100%");

        if(order.getOrderTime() != null) {
            orderTime.setValue(order.getOrderTime().toString(DateTimeFormat.mediumDateTime()));
        }
        orderTime.setSizeUndefined();
        orderTime.addStyleName(MyTheme.LABEL_BOLD);

        lineItemsCaption.addStyleName(MyTheme.LABEL_BOLD);
        order.getLineItems().forEach(lineItem -> lineItemsLayout.addComponent(
                new Label(lineItem.getQuantity() + " " + lineItem.getProduct().getName() + " – " + NumberFormat.getCurrencyInstance().format(lineItem.getProduct().getPrice()))
        ));

        orderTotal.setSizeUndefined();
        orderTotal.setValue(NumberFormat.getCurrencyInstance().format(order.getOrderTotal()));
        orderTotal.addStyleName(MyTheme.LABEL_BOLD);

        layout.setExpandRatio(lineItemsLayout, 1);
    }
}
