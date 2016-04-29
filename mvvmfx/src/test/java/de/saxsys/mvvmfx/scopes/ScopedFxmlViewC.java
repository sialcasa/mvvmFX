package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.Context;
import de.saxsys.mvvmfx.internal.InjectContext;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ScopedFxmlViewC implements FxmlView<ScopedViewModelC> {

    @InjectViewModel
    ScopedViewModelC viewModel;

    @FXML
    VBox root;

    @InjectContext
    Context context;

    ScopedFxmlViewD subViewDController;
    ScopedFxmlViewD subViewDWithoutContextController;

    public void initialize() {
        ViewTuple<ScopedFxmlViewD, ScopedViewModelD> load = FluentViewLoader.fxmlView(ScopedFxmlViewD.class)
                .context(context)
                .load();
        root.getChildren().add(load.getView());
        subViewDController = load.getCodeBehind();

        ViewTuple<ScopedFxmlViewD, ScopedViewModelD> load2 = FluentViewLoader.fxmlView(ScopedFxmlViewD.class).load();
        root.getChildren().add(load2.getView());
        subViewDWithoutContextController = load2.getCodeBehind();
    }

}
