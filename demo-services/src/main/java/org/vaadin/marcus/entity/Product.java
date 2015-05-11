package org.vaadin.marcus.entity;

import java.text.NumberFormat;

public class Product {
    private String name;
    private String description;
    // Don't ever use double for prices in real life or you'll have a bad time.
    // The BigDecimal API is just so horrible to work with for a demo.
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }


    public String getName() {
       return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " (" + NumberFormat.getCurrencyInstance().format(price) + ")";
    }
}
