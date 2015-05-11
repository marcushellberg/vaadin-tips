package org.vaadin.marcus.entity;

public class LineItem {
    private Product product;
    private int quantity = 1;
    private double discount = 0.0;

    public LineItem(Product product, int quantity, double discount) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
    }

    public LineItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
