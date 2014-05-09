package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class is used as example View class that uses FXML.
 * 
 * @author manuel.mauky 
 */
public class TestFxmlView implements FxmlView<TestViewModel>, Initializable{
    public URL url;
    public ResourceBundle resourceBundle;
    
    @InjectViewModel
    public TestViewModel viewModel;
    
    public boolean viewModelWasNull = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.url = url;
        this.resourceBundle = resourceBundle;
        
        viewModelWasNull = viewModel == null;
    }

}
