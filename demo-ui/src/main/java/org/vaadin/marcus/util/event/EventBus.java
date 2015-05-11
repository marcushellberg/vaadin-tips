package org.vaadin.marcus.util.event;

import org.vaadin.marcus.DemoUI;

/**
 * Convenience class for accessing the _UI Scoped_ EventBus. If you are using something like the CDI event
 * bus, you don't need a class like this.
 */
public class EventBus {

    public static void register(final Object listener) {
        DemoUI.getEventBus().register(listener);
    }

    public static void unregister(final Object listener) {
        DemoUI.getEventBus().unregister(listener);
    }

    public static void post(final Object event) {
        DemoUI.getEventBus().post(event);
    }
}
