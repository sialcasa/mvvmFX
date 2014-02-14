package de.saxsys.jfx.mvvm.viewloader;

import de.saxsys.jfx.mvvm.base.view.View;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class is used as example View class.
 */
public class TestView extends View<TestViewModel> {
    public URL url;
    public ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.url = url;
        this.resourceBundle = resourceBundle;
    }
}
