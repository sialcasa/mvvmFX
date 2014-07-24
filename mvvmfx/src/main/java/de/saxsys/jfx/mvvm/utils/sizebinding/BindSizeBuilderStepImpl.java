package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * Implementation of the Builder steps to bind the size of a given source component to the target component.
 *
 * @author manuel.mauky
 */
class BindSizeBuilderStepImpl implements SizeBindingsBuilder.BindSizeBuilderStep,
		SizeBindingsBuilder.FromBindSizeBuilderStep {
	private SizeBindingsBuilder.FromBindWidthBuilderStep widthStep;
	private SizeBindingsBuilder.FromBindHeightBuilderStep heightStep;
	
	@Override
	public void to(Region target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public void to(Control target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public void to(Rectangle target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public void to(ImageView target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(Region source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(Control source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(Rectangle source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(ImageView source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
}
