package de.saxsys.mvvmfx.scopes.example1.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class ScopedFxmlViewD implements FxmlView<ScopedViewModelD> {

    @FXML
    public ScopedFxmlViewF subviewFController;

    @FXML
    public ScopedFxmlViewG subviewGController;

    @InjectViewModel
    public ScopedViewModelD viewModel;

}
