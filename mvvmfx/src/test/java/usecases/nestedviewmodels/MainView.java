package usecases.nestedviewmodels;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * @author manuel.mauky
 */
public class MainView implements FxmlView<MainViewModel> {
	@FXML
	public SubView subview1Controller;
	@FXML
	public SubView subview2Controller;
	
	@InjectViewModel
	private MainViewModel viewModel;
	
	public void initialize(){
		viewModel.setSubViewModel1(subview1Controller.getViewModel());
		viewModel.setSubViewModel2(subview2Controller.getViewModel());
		
		viewModel.init();
	}
}
