package de.saxsys.mvvmfx.examples.synchronize;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SliderViewModel implements ViewModel {
	
	private final DoubleProperty sliderValue = new SimpleDoubleProperty();
	
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
}
