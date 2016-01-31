package de.saxsys.mvvmfx.examples.todomvc.ui.additems;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

/**
 * @author manuel.mauky
 */
public class AddItemsView implements FxmlView<AddItemsViewModel> {

    @FXML
    public CheckBox selectAll;
    @FXML
    public TextField addInput;

    @InjectViewModel
    private AddItemsViewModel viewModel;

    public void initialize() {
        addInput.textProperty().bindBidirectional(viewModel.newItemValueProperty());

        addInput.setOnAction(event -> viewModel.addItem());

        selectAll.selectedProperty().bindBidirectional(viewModel.allSelectedProperty());

        selectAll.visibleProperty().bind(viewModel.allSelectedVisibleProperty());
    }
    
}
