package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * Implementation of the Builder steps to unbind the width of a given component.
 *
 * @author manuel.mauky
 */
class UnbindWidthBuilderStepImpl implements SizeBindingsBuilder.UnbindStep {
	
	@Override
	public void of(Region source) {
		source.maxWidthProperty().unbind();
		source.minWidthProperty().unbind();
	}
	
	@Override
	public void of(Control source) {
		source.maxWidthProperty().unbind();
		source.minWidthProperty().unbind();
		
	}
	
	@Override
	public void of(Rectangle source) {
		source.widthProperty().unbind();
	}
	
	@Override
	public void of(ImageView source) {
		source.fitWidthProperty().unbind();
	}
}
