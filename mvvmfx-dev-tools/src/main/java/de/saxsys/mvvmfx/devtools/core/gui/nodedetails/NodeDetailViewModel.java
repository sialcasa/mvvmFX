package de.saxsys.mvvmfx.devtools.core.gui.nodedetails;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNode;
import de.saxsys.mvvmfx.devtools.core.analyzer.MvvmFxmlNode;
import de.saxsys.mvvmfx.devtools.core.gui.FxmlNodesScope;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NodeDetailViewModel implements ViewModel {


    private StringProperty fxmlNameText = new SimpleStringProperty();
    private StringProperty controllerText = new SimpleStringProperty();
    private StringProperty viewModelText = new SimpleStringProperty();
    private StringProperty directChildrenText = new SimpleStringProperty();
    private StringProperty totalChildrenText = new SimpleStringProperty();

    private ObservableList<String> usedScopesList = FXCollections.observableArrayList();
    private ObservableList<String> providedScopesList = FXCollections.observableArrayList();

    @InjectScope
    private FxmlNodesScope scope;

    public void initialize() {
        scope.selectedFxmlNodeProperty().addListener((observable, oldValue, newValue) -> setSelectedItem(newValue));
    }


    private void setSelectedItem(FxmlNode node) {
        usedScopesList.clear();
        providedScopesList.clear();

        if(node == null) {
            fxmlNameText.setValue("");
            controllerText.setValue("");
            viewModelText.setValue("");
            directChildrenText.setValue("");
            totalChildrenText.setValue("");
        } else {
            fxmlNameText.setValue(node.getFxmlPath());
            controllerText.setValue(showClassName(node.getControllerClass()));
            totalChildrenText.setValue(String.valueOf(node.getTotalChildren()));
            directChildrenText.setValue(String.valueOf(node.getDirectChildren()));


            if (node instanceof MvvmFxmlNode) {
                MvvmFxmlNode mvvmFxmlNode = (MvvmFxmlNode) node;
                viewModelText.setValue(showClassName(mvvmFxmlNode.getViewModelClass()));


                if(!mvvmFxmlNode.getScopes().isEmpty()){
                    mvvmFxmlNode.getScopes().stream()
                            .map(NodeDetailViewModel::showClassName)
                            .forEach(usedScopesList::add);
                }

                if(!mvvmFxmlNode.getProvidedScopes().isEmpty()){
                    mvvmFxmlNode.getProvidedScopes().stream()
                            .map(NodeDetailViewModel::showClassName)
                            .forEach(providedScopesList::add);
                }
            }
        }
    }


    private static String showClassName(Class<?> aClass) {
        return aClass.getSimpleName();
    }

    public ObservableList<String> getUsedScopesList() {
        return usedScopesList;
    }

    public ObservableList<String> getProvidedScopesList() {
        return providedScopesList;
    }

    public StringProperty fxmlNameTextProperty() {
        return fxmlNameText;
    }


    public StringProperty controllerTextProperty() {
        return controllerText;
    }


    public StringProperty viewModelTextProperty() {
        return viewModelText;
    }


    public StringProperty directChildrenTextProperty() {
        return directChildrenText;
    }

    public StringProperty totalChildrenTextProperty() {
        return totalChildrenText;
    }
}
