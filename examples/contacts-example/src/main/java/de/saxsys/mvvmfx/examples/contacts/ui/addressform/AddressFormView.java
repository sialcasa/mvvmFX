package de.saxsys.mvvmfx.examples.contacts.ui.addressform;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

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
	@FXML
	public Label countryLabel;
	
	@FXML
	public ProgressIndicator loadingIndicator;
	
	
	@InjectViewModel
	private AddressFormViewModel viewModel;
	
	
	public void initialize() {
		loadingIndicator.visibleProperty().bind(viewModel.loadingInProgressProperty());
		countryLabel.disableProperty().bind(viewModel.countryInputDisabledProperty());
		
		streetInput.textProperty().bindBidirectional(viewModel.streetProperty());
		postalcodeInput.textProperty().bindBidirectional(viewModel.postalCodeProperty());
		cityInput.textProperty().bindBidirectional(viewModel.cityProperty());
		
		countryInput.setItems(viewModel.countriesList());
		countryInput.valueProperty().bindBidirectional(viewModel.selectedCountryProperty());
		countryInput.disableProperty().bind(viewModel.countryInputDisabledProperty());
		
		federalStateInput.setItems(viewModel.subdivisionsList());
		federalStateInput.valueProperty().bindBidirectional(viewModel.selectedSubdivisionProperty());
		federalStateInput.disableProperty().bind(viewModel.subdivisionInputDisabledProperty());
		
		subdivisionLabel.textProperty().bind(viewModel.subdivisionLabel());
		subdivisionLabel.disableProperty().bind(viewModel.subdivisionInputDisabledProperty());
	}
}
