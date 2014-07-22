package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TestFxmlViewFxRoot extends VBox implements FxmlView<TestViewModel> {

    @InjectViewModel
    public TestViewModel viewModel;

    public boolean viewModelWasNull = true;

    public void initialize() {
        viewModelWasNull = viewModel == null;
    }
}
