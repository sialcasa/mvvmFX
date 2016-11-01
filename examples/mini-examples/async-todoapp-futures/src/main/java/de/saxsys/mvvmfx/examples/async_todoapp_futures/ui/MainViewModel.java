package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;


public class MainViewModel implements ViewModel {

    @InjectScope
    private TodoScope todoScope;

    private ReadOnlyStringWrapper errorText = new ReadOnlyStringWrapper();
    private ReadOnlyBooleanWrapper errorVisible = new ReadOnlyBooleanWrapper();

    public void initialize() {
        errorText.bind(Bindings.createStringBinding(() -> {
            final Throwable throwable = todoScope.errorProperty().getValue();

            if(throwable != null) {
                return throwable.getLocalizedMessage();
            } else {
                return "";
            }
        }, todoScope.errorProperty()));

        errorVisible.bind(todoScope.errorProperty().isNotNull());
    }

    public ReadOnlyStringProperty errorTextProperty() {
        return errorText.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty errorVisibleProperty() {
        return errorVisible.getReadOnlyProperty();
    }
}
