package org.vaadin.marcus.ui.views;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ContainerDataSeries;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import org.vaadin.marcus.components.VerticalSpacedLayout;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.service.OrderService;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(uri="chart", displayName = "Chart")
public class ChartView extends VerticalSpacedLayout implements View {

    private OrderService orderService = OrderService.get();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addChart();
    }

    private void addChart() {
        BeanItemContainer<Order> container = new BeanItemContainer<>(Order.class, orderService.fetchOrders(0, 250));
        ContainerDataSeries dataSeries = new ContainerDataSeries(container);

        dataSeries.setXPropertyId("orderTime");
        dataSeries.setYPropertyId("orderTotal");

        Chart chart = new Chart();
        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.LINE);
        configuration.getTitle().setText("Sales over time");
        configuration.setSeries(dataSeries);

        chart.drawChart(configuration);

        chart.setWidth("100%");
        addComponent(chart);
    }
}
