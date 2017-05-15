package de.saxsys.mvvmfx.devtools.core.gui;

import de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNode;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Collections;

public class FxmlNodeTreeItem {

    private FxmlNode node;
    private StringProperty value = new SimpleStringProperty();

    private ObservableList<FxmlNodeTreeItem> children = FXCollections.observableArrayList();

    public FxmlNodeTreeItem(FxmlNode node) {
        this.node = node;
        if(node == null) {
            value.set("");
        } else {
            value.set(node.getControllerClass().getSimpleName());
        }
    }

    public FxmlNode getNode() {
        return node;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public String getValue() {
        return value.get();
    }

    public ReadOnlyStringProperty valueProperty() {
        return value;
    }

    public void addChildren(FxmlNodeTreeItem...items) {
        Collections.addAll(this.children, items);
    }

    public void removeChildren(FxmlNodeTreeItem ... items) {
        children.removeAll(Arrays.asList(items));
    }

    public void clearChildren() {
        children.clear();
    }

    public ObservableList<FxmlNodeTreeItem> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return value.get();
    }
}
