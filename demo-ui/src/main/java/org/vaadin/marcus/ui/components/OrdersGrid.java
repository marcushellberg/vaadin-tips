package org.vaadin.marcus.ui.components;


import com.vaadin.data.Container;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import org.vaadin.marcus.util.converter.CurrencyConverter;
import org.vaadin.marcus.util.converter.DateTimeConverter;
import org.vaadin.marcus.util.converter.LineItemConverter;
import org.vaadin.marcus.util.converter.PercentageConverter;

public class OrdersGrid extends Grid {

    public void setContainer(Container.Indexed container) {
        setContainerDataSource(container);

        removeColumn("id");

        setColumnOrder("orderTime", "progress", "lineItems", "orderTotal", "status");

        getColumn("progress").setConverter(new PercentageConverter());
        getColumn("progress").setRenderer(new ProgressBarRenderer());

        getColumn("orderTime").setConverter(new DateTimeConverter());
        getColumn("lineItems").setConverter(new LineItemConverter());
        getColumn("lineItems").setMaximumWidth(300);
        getColumn("orderTotal").setConverter(new CurrencyConverter());

        getColumn("orderTime").setLastFrozenColumn();
    }


}
