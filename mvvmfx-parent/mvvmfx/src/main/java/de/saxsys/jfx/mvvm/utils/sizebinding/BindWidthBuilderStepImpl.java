package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;


/**
 * Implementation of the Builder steps to bind the width of a given source component to the target component.
 *
 * @author manuel.mauky
 */
class BindWidthBuilderStepImpl implements SizeBindingsBuilder.BindWidthBuilderStep, SizeBindingsBuilder.FromBindWidthBuilderStep {

    private ReadOnlyDoubleProperty width;

    @Override
    public void to(Region target) {
        target.maxWidthProperty().bind(width);
        target.minWidthProperty().bind(width);
    }
    @Override
    public void to(Control target) {
        target.maxWidthProperty().bind(width);
        target.minWidthProperty().bind(width);
    }
    @Override
    public void to(Rectangle target) {
        target.widthProperty().bind(width);
    }
    @Override
    public void to(ImageView target){
        target.fitWidthProperty().bind(width);
    }
    @Override
    public SizeBindingsBuilder.FromBindWidthBuilderStep from(Region source) {
        width = source.widthProperty();
        return this;
    }
    @Override
    public SizeBindingsBuilder.FromBindWidthBuilderStep from(Control source){
        width = source.widthProperty();
        return this;
    }
    @Override
    public SizeBindingsBuilder.FromBindWidthBuilderStep from(Rectangle source) {
        width = source.widthProperty();
        return this;
    }
    @Override
    public SizeBindingsBuilder.FromBindWidthBuilderStep from(ImageView source){
        width = source.fitWidthProperty();
        return this;
    }
}
