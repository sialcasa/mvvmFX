package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainView implements FxmlView<MainViewModel> {

    @FXML
    public Label errorLabel;

    @InjectViewModel
    private MainViewModel viewModel;

    public void initialize() {
        errorLabel.textProperty().bind(viewModel.errorTextProperty());

        errorLabel.visibleProperty().bind(viewModel.errorVisibleProperty());
    }
}
