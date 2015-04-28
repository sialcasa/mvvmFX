package usecases.nestedviewmodels;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author manuel.mauky
 */
public class SubSubView implements FxmlView<SubSubViewModel> {
	@FXML
	public Label number;
	
	@InjectViewModel
	private SubSubViewModel viewModel;
	
	public void initialize(){
		number.textProperty().bind(viewModel.numberProperty().asString());
	}
	
	public SubSubViewModel getViewModel(){
		return viewModel;
	}
}
