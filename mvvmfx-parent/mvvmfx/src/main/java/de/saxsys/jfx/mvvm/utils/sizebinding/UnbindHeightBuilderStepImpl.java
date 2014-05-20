package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * Implementation of the Builder steps to unbind the height of a given component.
 *
 * @author manuel.mauky
 */
class UnbindHeightBuilderStepImpl implements SizeBindingsBuilder.UnbindStep {
    @Override
    public void of(Region source) {
        source.maxHeightProperty().unbind();
        source.minHeightProperty().unbind();
    }

    @Override
    public void of(Control source) {
        source.maxHeightProperty().unbind();
        source.minHeightProperty().unbind();
    }

    @Override
    public void of(Rectangle source) {
        source.heightProperty().unbind();
    }

    @Override
    public void of(ImageView source) {
        source.fitHeightProperty().unbind();
    }
}
