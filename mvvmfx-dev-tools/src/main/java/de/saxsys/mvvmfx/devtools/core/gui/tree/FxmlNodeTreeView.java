package de.saxsys.mvvmfx.devtools.core.gui.tree;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.devtools.core.gui.FxmlNodeTreeItem;
import de.saxsys.mvvmfx.devtools.core.gui.RecursiveTreeItem;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FxmlNodeTreeView implements FxmlView<FxmlNodeTreeViewModel>{

    @FXML
    private TreeView<FxmlNodeTreeItem> treeView;

    @InjectViewModel
    FxmlNodeTreeViewModel viewModel;


    private TreeItem<FxmlNodeTreeItem> rootItem;

    @FXML
    void initialize() {
        rootItem = new RecursiveTreeItem<>(viewModel.getRootItemValue(), FxmlNodeTreeItem::getChildren);

        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);

        rootItem.setValue(viewModel.getRootItemValue());

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newTreeItem) -> {
            if (newTreeItem == null) {
                viewModel.selectedTreeItemProperty().setValue(null);
            } else {
                viewModel.selectedTreeItemProperty().set(newTreeItem.getValue());
            }
        });
    }




}
