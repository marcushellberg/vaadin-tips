package org.vaadin.marcus.ui.views;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.marcus.util.ViewConfig;

@ViewConfig(
        uri="heap-destroyer",
        displayName = "Heap Destroyer",
        createMode = ViewConfig.CreateMode.ALWAYS_NEW)
public class HeapDestroyerView extends VerticalLayout implements View {

    public static final int BIGNUM = 10000;

    private long[][] largeArray = new long[BIGNUM][BIGNUM];

    public HeapDestroyerView() {
        setMargin(true);
        for (int i = 0; i < BIGNUM; i++) {
            for (int j = 0; j < BIGNUM; j++) {
                largeArray[i][j] = i * j;
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addComponent(new Label("Nom nom nom, your heap was delicious."));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(new Label("Loaded view"));
    }
}
