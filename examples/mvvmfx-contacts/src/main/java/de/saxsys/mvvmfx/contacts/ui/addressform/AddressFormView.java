package de.saxsys.mvvmfx.contacts.ui.addressform;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddressFormView implements FxmlView<AddressFormViewModel> {
	@FXML
	public TextField streetInput;
	@FXML
	public TextField postalcodeInput;
	@FXML
	public TextField cityInput;
	@FXML
	public ChoiceBox countryInput;
	@FXML
	public ComboBox federalStateInput;
	
	
	@InjectViewModel
	private AddressFormViewModel viewModel;
	
	
	public void initialize(){
		
	}
}
