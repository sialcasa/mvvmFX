package de.saxsys.jfx.mvvm.viewloader;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.base.view.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class is used as example View class.
 */
public class TestView implements FxmlView<TestViewModel>, Initializable{
    public URL url;
    public ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.url = url;
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void setViewModel(TestViewModel viewModel) {

    }
}
