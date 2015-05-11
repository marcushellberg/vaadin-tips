package org.vaadin.marcus.ui.views.form;

import com.vaadin.data.fieldgroup.FieldGroup;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.service.OrderService;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class FormPresenterTest {

    @Mock
    OrderService orderService;

    @Mock
    FormView view;

    private FormPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new FormPresenter(view);

        // Notice: here, we're using a mocked service to unit test the presenter logic in isolation
        // If you want to do an integration test, wire up the actual service class so you can
        // exercise the full stack
        presenter.orderService = orderService;
    }

    @Test
    public void testInit() {
        presenter.viewShown();
        assertNotNull("A new order did not get created", presenter.order);
        verify(view).setOrder(presenter.order);
    }


    @Test
    public void testSuccessfulSave() throws FieldGroup.CommitException {
        Order order = new Order();
        presenter.order = order;

        presenter.savePressed();

        verify(view).commit();
        verify(orderService).saveOrder(order);
        verify(view).showSuccess();

    }

    @Test
    public void testFailedSave() throws FieldGroup.CommitException {
        doThrow(new FieldGroup.CommitException()).when(view).commit();
        presenter.savePressed();
        verify(view).showFailure();
    }
}
