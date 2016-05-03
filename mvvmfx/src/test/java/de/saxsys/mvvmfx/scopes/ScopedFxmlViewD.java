package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ScopedFxmlViewD implements FxmlView<ScopedViewModelD> {

    @FXML
    public ScopedFxmlViewF subviewFController;

    @FXML
    public ScopedFxmlViewG subviewGController;

    @InjectViewModel
    ScopedViewModelD viewModel;

}
