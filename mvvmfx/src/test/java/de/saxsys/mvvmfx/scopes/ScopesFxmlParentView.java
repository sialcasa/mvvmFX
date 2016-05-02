package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import javafx.fxml.FXML;

public class ScopesFxmlParentView implements FxmlView<ViewModel> {
    @FXML
    public ScopedFxmlViewA subviewAController;
    @FXML
    public ScopedFxmlViewB subviewBController;
}
