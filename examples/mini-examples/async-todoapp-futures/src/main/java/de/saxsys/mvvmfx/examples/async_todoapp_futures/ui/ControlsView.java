package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControlsView implements FxmlView<ControlsViewModel> {

    @FXML
    public TextField input;
    @FXML
    public Button addButton;
    @FXML
    public Button removeButton;

    @InjectViewModel
    private ControlsViewModel viewModel;

    public void initialize() {
        input.textProperty().bindBidirectional(viewModel.inputProperty());

        addButton.disableProperty().bind(viewModel.addPossibleProperty().not());
        removeButton.disableProperty().bind(viewModel.deletePossibleProperty().not());
    }

    public void add() {
        viewModel.addItem();
    }

    public void remove() {
        viewModel.removeItem();
    }
}