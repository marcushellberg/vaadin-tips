package org.vaadin.marcus.service;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.joda.time.DateTime;
import org.vaadin.marcus.entity.LineItem;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.entity.Product;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;

public class OrderService {
    // To keep the tech stack simple, I opted to avoid using a container like JavaEE or Spring
    // In most cases, I would  have the Service managed by the container and inject it where needed.
    private static final OrderService INSTANCE = new OrderService();
    private final List<Product> products;

    public static OrderService get() {
        return INSTANCE;
    }

    private static final int NUM_ORDERS = 10000;
    private Random randy = new Random();
    private final LinkedList<Order> orders;

    private ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

    private OrderService() {
        products = createProducts();
        orders = initOrders();
    }

    private List<Product> createProducts() {
        return Lists.newArrayList(
                new Product("Apple", 1),
                new Product("Orange", 2),
                new Product("Banana", 3));
    }

    /**
     * Synchronous method that returns all orders and is slow.
     */
    public List<Order> getOrders() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Async method for getting orders.
     */
    public ListenableFuture<List<Order>> getOrdersAsync() {
        return service.submit(OrderService.this::getOrders);
    }

    /**
     * Returns a max of 45 (LazyList default page size) orders starting at the given index.
     *
     * @param startIndex
     * @return
     */
    public List<Order> fetchOrders(int startIndex) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fetchOrders(startIndex, 45);
    }

    /**
     * Returns a slice of orders
     *
     * @param startIndex
     * @param num
     * @return
     */
    public List<Order> fetchOrders(int startIndex, int num) {
        int endIndex = startIndex + num > orders.size() ? orders.size() : startIndex + num;
        return orders.subList(startIndex, endIndex);
    }

    /**
     * @return the totan number of orders
     */
    public int getOrderCount() {
        return orders.size();
    }

    /**
     * Save a new order
     *
     * @param order
     */
    public void saveOrder(Order order) {
        orders.add(0, order);
    }

    public List<Product> getProducts() {
        return products;
    }

    /**
     * Generates some random orders
     *
     * @return
     */
    private LinkedList<Order> initOrders() {
        LinkedList<Order> orders;
        orders = new LinkedList<>();
        for (int i = 0; i < NUM_ORDERS; i++) {
            LinkedList<LineItem> lineItems = new LinkedList<>();
            for (int j = 0; j <= (1 + randy.nextInt(2)); j++) {
                lineItems.add(generateLineItem());
            }
            DateTime orderTime = DateTime.now().minusDays(randy.nextInt(30)).minusHours(randy.nextInt(23)).minusMinutes(randy.nextInt(59));
            orders.add(new Order(orderTime, getRandomStatus(), lineItems));
        }
        return orders;
    }

    public Order findById(UUID uuid) {
        return orders.stream().filter(order -> order.getId().equals(uuid)).findFirst().orElse(null);
    }

    private Order.Status getRandomStatus() {
        Order.Status[] statuses = Order.Status.values();
        return statuses[randy.nextInt(statuses.length)];
    }

    private LineItem generateLineItem() {
        return new LineItem(getRandomProduct(), (1 + randy.nextInt(2)), randy.nextDouble());
    }

    private Product getRandomProduct() {
        return products.get(randy.nextInt(2));
    }
}
