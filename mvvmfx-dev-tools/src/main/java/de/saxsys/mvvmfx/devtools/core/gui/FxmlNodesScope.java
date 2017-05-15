package de.saxsys.mvvmfx.devtools.core.gui;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FxmlNodesScope implements Scope {


    private ObservableList<FxmlNode> fxmlNodes = FXCollections.observableArrayList();

    private ObjectProperty<FxmlNode> selectedFxmlNode = new SimpleObjectProperty<>();

    public ObservableList<FxmlNode> getFxmlNodes() {
        return fxmlNodes;
    }

    public FxmlNode getSelectedFxmlNode() {
        return selectedFxmlNode.get();
    }

    public ObjectProperty<FxmlNode> selectedFxmlNodeProperty() {
        return selectedFxmlNode;
    }

    public void setSelectedFxmlNode(FxmlNode selectedFxmlNode) {
        this.selectedFxmlNode.set(selectedFxmlNode);
    }
}
