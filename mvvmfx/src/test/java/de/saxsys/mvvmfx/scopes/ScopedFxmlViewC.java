package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.Context;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectContext;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope1;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope2;
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
                .providedScopes(new TestScope2())
                .load();
        root.getChildren().add(load.getView());
        subViewDController = load.getCodeBehind();
    }

    public void loadWrongScopedView() {
        ViewTuple<ScopedFxmlViewD, ScopedViewModelD> load2 = FluentViewLoader.fxmlView(ScopedFxmlViewD.class).load();
        root.getChildren().add(load2.getView());
        subViewDWithoutContextController = load2.getCodeBehind();
    }

    public void loadCorrectScopedView() {
        ViewTuple<ScopedFxmlViewD, ScopedViewModelD> load2 = FluentViewLoader.fxmlView(ScopedFxmlViewD.class)
                .providedScopes(new TestScope1(), new TestScope2())
                .load();
        root.getChildren().add(load2.getView());
        subViewDWithoutContextController = load2.getCodeBehind();
    }

}
