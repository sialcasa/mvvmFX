package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.JavaView;
import javafx.scene.layout.VBox;

/**
 * This class is used as example View class that is written in pure java. 
 */
public class TestJavaView extends VBox implements JavaView<TestViewModel>{
    public TestViewModel viewModel;

    @Override
    public void setViewModel(TestViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
