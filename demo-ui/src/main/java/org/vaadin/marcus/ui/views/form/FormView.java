package org.vaadin.marcus.ui.views.form;


import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.ui.components.VerticalSpacedLayout;
import org.vaadin.marcus.util.FieldGroupUtil;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(uri = "form", displayName = "Form")
public class FormView extends VerticalSpacedLayout implements View {

    private final FormPresenter presenter;
    private FormLayout formLayout;
    private FieldGroup fieldGroup;
    private Button clear;
    private Button save;

    public FormView() {
        presenter = new FormPresenter(this);

        addCaption();
        addForm();
    }

    private void addCaption() {
        Label caption = new Label("Add new order");
        caption.addStyleName(MyTheme.LABEL_H1);
        addComponent(caption);
    }

    private void addForm() {
        formLayout = new FormLayout();
        addComponents(formLayout, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        clear = new Button("Clear", click -> presenter.clearPressed());
        save = new Button("Add Order", click -> presenter.savePressed());
        buttonsLayout.addComponents(clear, save);

        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth("100%");

        clear.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addStyleName(MyTheme.BUTTON_PRIMARY);

        buttonsLayout.setExpandRatio(clear, 1);
        buttonsLayout.setComponentAlignment(clear, Alignment.TOP_RIGHT);


        return buttonsLayout;
    }

    public void setOrder(Order order) {
        fieldGroup = new FieldGroup(new BeanItem<>(order));
        fieldGroup.bindMemberFields(formLayout);
        FieldGroupUtil.improveUX(fieldGroup, save, clear);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        presenter.viewShown();
    }

    public void showSuccess() {
        Notification.show("Saved successfully");
    }

    public void showFailure() {
        Notification.show("You are doing it wrong.", Notification.Type.ERROR_MESSAGE);
    }

    public void commit() throws FieldGroup.CommitException {
        fieldGroup.commit();
    }
}
