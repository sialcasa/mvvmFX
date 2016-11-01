package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class ListItem<ID> {

    private final ID id;
    private StringProperty label = new SimpleStringProperty();

    public ListItem(ID id) {
        this.id = id;
    }

    public ListItem(ID id, String label) {
        this(id);
        this.label.setValue(label);
    }

    public ID getId() {
        return id;
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListItem<?> listItem = (ListItem<?>) o;
        return Objects.equals(id, listItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
