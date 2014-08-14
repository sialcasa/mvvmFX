package de.saxsys.mvvmfx.examples.synchronize;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SliderView implements FxmlView<SliderViewModel> {
	
	
	@FXML
	private Label myLabel;
	
	@FXML
	private Slider mySlider;
	
	@InjectViewModel
	private SliderViewModel viewModel;
	
	
	public void initialize(){
		mySlider.valueProperty().bindBidirectional(viewModel.sliderValueProperty());
		
		myLabel.textProperty().bind(Bindings.format("Current Value: %1$.1f", viewModel.sliderValueProperty()));
	}
}
