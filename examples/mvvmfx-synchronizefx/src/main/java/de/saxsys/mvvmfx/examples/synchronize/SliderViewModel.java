package de.saxsys.mvvmfx.examples.synchronize;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;

public class SliderViewModel implements ViewModel {
	
	private final DoubleProperty sliderValue = new SimpleDoubleProperty();
	
	private final ReadOnlyStringWrapper labelText = new ReadOnlyStringWrapper();
	
	public SliderViewModel(){
		labelText.bind(Bindings.format("Current Value: %1$.1f", sliderValue));
	}
	
	/**
	 * @return the slider value as property.
	 */
	public DoubleProperty sliderValueProperty() {
		return sliderValue;
	}
	/**
	 * @param value
	 * the new value for the slider.
	 */
	public void setSliderValue(final double value) {
		sliderValue.set(value);
	}
	/**
	 * @return the current slider value.
	 */
	public double getSliderValue() {
		return sliderValue.get();
	}

	/**
	 * @return the text value of the label as read only string property.
	 */
	public ReadOnlyStringProperty labelTextProperty(){
		return labelText.getReadOnlyProperty();
	}

	/**
	 * @return the text value of the label.
	 */
	public String getLabelText(){
		return labelText.get();
	}
}
