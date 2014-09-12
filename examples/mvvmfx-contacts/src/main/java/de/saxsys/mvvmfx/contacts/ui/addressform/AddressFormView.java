package de.saxsys.mvvmfx.contacts.ui.addressform;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class AddressFormView implements FxmlView<AddressFormViewModel> {
	@FXML
	public TextField streetInput;
	@FXML
	public TextField postalcodeInput;
	@FXML
	public TextField cityInput;
	@FXML
	public ComboBox<String> countryInput;
	@FXML
	public ComboBox<String> federalStateInput;
	@FXML
	public Label subdivisionLabel;
	
	
	@InjectViewModel
	private AddressFormViewModel viewModel;
	
	
	public void initialize(){
		subdivisionLabel.textProperty().bind(viewModel.subdivisionLabel());
		
		streetInput.textProperty().bindBidirectional(viewModel.streetProperty());
		postalcodeInput.textProperty().bindBidirectional(viewModel.postalCodeProperty());
		cityInput.textProperty().bindBidirectional(viewModel.cityProperty());
		
		countryInput.setItems(viewModel.countriesList());
		countryInput.valueProperty().bindBidirectional(viewModel.selectedCountryProperty());
		
		federalStateInput.setItems(viewModel.subdivisionsList());
		federalStateInput.valueProperty().bindBidirectional(viewModel.selectedSubdivisionProperty());
	}
}
