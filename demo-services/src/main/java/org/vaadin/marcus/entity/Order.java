package org.vaadin.marcus.entity;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public class Order {
    public enum Status {RECEIVED, PROCESSING, CHARGED, PREPARING_FOR_SHIPMENT, SHIPPED}

    private UUID id;
    private DateTime orderTime;
    private Status status;
    private List<LineItem> lineItems = Lists.newLinkedList();

    public Order() {
        id = UUID.randomUUID();
    }

    public Order(DateTime orderTime, Status status, List<LineItem> lineItems) {
        this();
        this.orderTime = orderTime;
        this.status = status;
        this.lineItems = lineItems;
    }

    public UUID getId() {
        return id;
    }

    public DateTime getOrderTime() {
        return orderTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addProduct(Product product, int quantity, double discount) {
        getLineItems().add(new LineItem(product, quantity, discount));
    }

    public double getOrderTotal() {
        return getLineItems()
                .stream()
                .mapToDouble(lineItem ->
                        lineItem.getQuantity()
                                * lineItem.getProduct().getPrice()
                                * (1-lineItem.getDiscount()))
                .sum();
    }
}
