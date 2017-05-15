package de.saxsys.mvvmfx.devtools.core.gui.nodedetails;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NodeDetailView implements FxmlView<NodeDetailViewModel> {


    @FXML
    private Label fxmlNameLabel;

    @FXML
    private Label controllerLabel;

    @FXML
    private Label viewModelLabel;

    @FXML
    private Label directChildrenLabel;

    @FXML
    private Label totalChildrenLabel;

    @FXML
    private HBox scopesListHBox;

    @FXML
    private VBox scopeListVBox;

    @FXML
    private HBox providedScopeListHBox;

    @FXML
    private VBox providedScopeListVBox;

    @InjectViewModel
    NodeDetailViewModel viewModel;

    @FXML
    void initialize() {
        fxmlNameLabel.textProperty().bind(viewModel.fxmlNameTextProperty());
        controllerLabel.textProperty().bind(viewModel.controllerTextProperty());
        viewModelLabel.textProperty().bind(viewModel.viewModelTextProperty());
        directChildrenLabel.textProperty().bind(viewModel.directChildrenTextProperty());
        totalChildrenLabel.textProperty().bind(viewModel.totalChildrenTextProperty());


        scopesListHBox.visibleProperty().bind(Bindings.isNotEmpty(viewModel.getUsedScopesList()));
        providedScopeListHBox.visibleProperty().bind(Bindings.isNotEmpty(viewModel.getProvidedScopesList()));


        viewModel.getUsedScopesList().addListener((ListChangeListener<String>) c -> {
            ObservableList<? extends String> list = c.getList();
            scopeListVBox.getChildren().clear();
            list.forEach(scopeName -> {
                Label scopeLabel = new Label(scopeName);
                scopeListVBox.getChildren().add(scopeLabel);
            });
        });

        viewModel.getProvidedScopesList().addListener((ListChangeListener<String>) c -> {
            ObservableList<? extends String> list = c.getList();
            providedScopeListVBox.getChildren().clear();

            list.forEach(providedScopeName -> {
                Label providedScopeLabel = new Label(providedScopeName);
                providedScopeListVBox.getChildren().add(providedScopeLabel);
            });

        });



    }
}
