package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * Implementation of the Builder steps to unbind the size of a given component.
 *
 * @author manuel.mauky
 */
class UnbindSizeBuilderStepImpl implements SizeBindingsBuilder.UnbindStep {
    @Override
    public void of(Region source) {
        new UnbindWidthBuilderStepImpl().of(source);
        new UnbindHeightBuilderStepImpl().of(source);
    }

    @Override
    public void of(Control source) {
        new UnbindWidthBuilderStepImpl().of(source);
        new UnbindHeightBuilderStepImpl().of(source);
    }

    @Override
    public void of(Rectangle source) {
        new UnbindWidthBuilderStepImpl().of(source);
        new UnbindHeightBuilderStepImpl().of(source);
    }

    @Override
    public void of(ImageView source) {
        new UnbindWidthBuilderStepImpl().of(source);
        new UnbindHeightBuilderStepImpl().of(source);
    }
}
