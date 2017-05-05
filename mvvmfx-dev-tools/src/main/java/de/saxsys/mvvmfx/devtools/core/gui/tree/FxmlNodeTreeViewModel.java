package de.saxsys.mvvmfx.devtools.core.gui.tree;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNode;
import de.saxsys.mvvmfx.devtools.core.gui.FxmlNodeTreeItem;
import de.saxsys.mvvmfx.devtools.core.gui.FxmlNodesScope;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;

public class FxmlNodeTreeViewModel implements ViewModel {

    @InjectScope
    private FxmlNodesScope scope;

    private FxmlNodeTreeItem rootItemValue = new FxmlNodeTreeItem(null);

    private ObjectProperty<FxmlNodeTreeItem> selectedTreeItem = new SimpleObjectProperty<>();

    public void initialize() {
        fillTreeItems();
        scope.getFxmlNodes().addListener((ListChangeListener<FxmlNode>) c -> {
            fillTreeItems();
        });

        selectedTreeItem.addListener((observable, oldValue, newValue) -> {
            if(newValue == null) {
                scope.setSelectedFxmlNode(null);
            } else {
                scope.setSelectedFxmlNode(newValue.getNode());
            }
        });
    }

    private void fillTreeItems() {
        rootItemValue.clearChildren();

        scope.getFxmlNodes().forEach(fxmlNode -> {
            createTreeItems(fxmlNode, rootItemValue);
        });
    }


    private FxmlNodeTreeItem createTreeItems(FxmlNode node, FxmlNodeTreeItem parent) {
        FxmlNodeTreeItem newItem = new FxmlNodeTreeItem(node);

        node.getFxIncludes().forEach(child -> createTreeItems(child, newItem));

        parent.addChildren(newItem);
        return parent;
    }

    public FxmlNodeTreeItem getRootItemValue() {
        return rootItemValue;
    }

    public ObjectProperty<FxmlNodeTreeItem> selectedTreeItemProperty() {
        return selectedTreeItem;
    }
}
