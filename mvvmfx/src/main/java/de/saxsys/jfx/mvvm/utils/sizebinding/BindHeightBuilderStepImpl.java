package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * Implementation of the Builder steps to bind the height of a given source component to the target component.
 * 
 * @author manuel.mauky 
 */
class BindHeightBuilderStepImpl implements SizeBindingsBuilder.BindHeightBuilderStep, SizeBindingsBuilder.FromBindHeightBuilderStep {

    private ReadOnlyDoubleProperty height;

    @Override
    public void to(Region target) {
        target.maxHeightProperty().bind(height);
        target.minHeightProperty().bind(height);
    }
    @Override
    public void to(Control target) {
        target.maxHeightProperty().bind(height);
        target.minHeightProperty().bind(height);
    }
    @Override
    public void to(Rectangle target) {
        target.heightProperty().bind(height);
    }
    @Override
    public void to(ImageView target){
        target.fitHeightProperty().bind(height);
    }
    @Override
    public SizeBindingsBuilder.FromBindHeightBuilderStep from(Region source) {
        height = source.heightProperty();
        return this;
    }
    @Override
    public SizeBindingsBuilder.FromBindHeightBuilderStep from(Control source) {
        height = source.heightProperty();
        return this;
    }
    @Override
    public SizeBindingsBuilder.FromBindHeightBuilderStep from(Rectangle source) {
        height = source.heightProperty();
        return this;
    }
    @Override
    public SizeBindingsBuilder.FromBindHeightBuilderStep from(ImageView source){
        height = source.fitHeightProperty();
        return this;
    }
}
