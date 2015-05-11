package org.vaadin.marcus.components;


import com.vaadin.data.Container;
import com.vaadin.ui.Grid;
import org.vaadin.marcus.util.converter.CurrencyConverter;
import org.vaadin.marcus.util.converter.DateTimeConverter;
import org.vaadin.marcus.util.converter.LineItemConverter;

public class OrdersGrid extends Grid {

    public void setContainer(Container.Indexed container) {
        setContainerDataSource(container);

        removeColumn("id");

        setColumnOrder("orderTime", "lineItems", "orderTotal", "status");

        getColumn("orderTime").setConverter(new DateTimeConverter());
        getColumn("lineItems").setConverter(new LineItemConverter());
        getColumn("lineItems").setMaximumWidth(300);
        getColumn("orderTotal").setConverter(new CurrencyConverter());
        getColumn("orderTime").setLastFrozenColumn();
    }


}
