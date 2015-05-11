package org.vaadin.marcus.ui.views.form;

import com.vaadin.data.fieldgroup.FieldGroup;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.service.OrderService;

public class FormPresenter {

    protected FormView view;
    protected Order order;
    protected OrderService orderService = OrderService.get();

    public FormPresenter(FormView view) {
        this.view = view;
    }

    public void viewShown() {
        clearForm();
    }

    public void clearPressed(){
        clearForm();
    }

    public void savePressed() {
        try {
            view.commit();
            orderService.saveOrder(order);
            view.showSuccess();
            clearForm();
        }
        // Note that our View is leaking its internal implementation by throwing a FieldGroup.CommitException.
        // I opted for a simple an short solution, but purists may want to avoid.
        catch (FieldGroup.CommitException e) {
            view.showFailure();
        }

    }

    private void clearForm() {
        order = new Order();
        view.setOrder(order);
    }
}
