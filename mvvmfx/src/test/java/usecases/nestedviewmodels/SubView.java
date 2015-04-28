package usecases.nestedviewmodels;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author manuel.mauky
 */
public class SubView implements FxmlView<SubViewModel> {
	@FXML
	public Label number;
	@FXML
	public SubSubView subsubview1Controller;
	@FXML
	public SubSubView subsubview2Controller;
	@FXML
	public SubSubView subsubview3Controller;

	@InjectViewModel
	private SubViewModel viewModel;

	public void initialize(){
		viewModel.setSubSubViewModel1(subsubview1Controller.getViewModel());
		viewModel.setSubSubViewModel2(subsubview2Controller.getViewModel());
		viewModel.setSubSubViewModel3(subsubview3Controller.getViewModel());

		
		viewModel.init();
		
		number.textProperty().bind(viewModel.numberProperty().asString());
	}
	
	public SubViewModel getViewModel(){
		return viewModel;
	}
}
