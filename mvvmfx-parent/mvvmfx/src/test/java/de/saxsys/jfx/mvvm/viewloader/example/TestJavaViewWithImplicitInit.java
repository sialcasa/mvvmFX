package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.InjectViewModel;
import de.saxsys.jfx.mvvm.api.JavaView;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

/**
 * This class is used as example View class. It is used to verify that
 * the same implicit initialization that is done for fxml views by the {@link javafx.fxml.FXMLLoader}
 * is also working for Java-written Views by the mvvmfx framework.
 */
public class TestJavaViewWithImplicitInit extends VBox implements JavaView<TestViewModel>{
    public ResourceBundle resources;
        
    @InjectViewModel
    public TestViewModel viewModel;
    
    public boolean wasInitialized;
    public boolean viewModelWasNull = true;
    public boolean resourcesWasNull = true;
            
    public void initialize(){
        wasInitialized = true;
        
        resourcesWasNull = resources == null;
        viewModelWasNull = viewModel == null;
    }
}
