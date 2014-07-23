package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;


/**
 * This class is a helper for binding/unbind the size of two components.
 * It can handle the following component types:
 * <p/>
 * <ul>
 * <li>{@link javafx.scene.layout.Region}</li>
 * <li>{@link javafx.scene.control.Control}</li>
 * <li>{@link javafx.scene.shape.Rectangle}</li>
 * <li>{@link javafx.scene.image.ImageView}</li>
 * </ul>
 * 
 * Usage example: 
 * 
 * <pre>
 *     VBox box = new VBox();
 *     
 *     Rectangle rect = new Rectangle();
 *     
 *     SizeBindingsBuilder.bindSize().from(box).to(rect);
 *     
 * </pre>
 * 
 * @author manuel.mauky 
 * 
 */
public class SizeBindingsBuilder {

    public static BindSizeBuilderStep bindSize() {
        return new BindSizeBuilderStepImpl();
    }

    public static BindWidthBuilderStep bindWidth() {
        return new BindWidthBuilderStepImpl();
    }

    public static BindHeightBuilderStep bindHeight() {
        return new BindHeightBuilderStepImpl();
    }


    public static UnbindStep unbindSize() {
        return new UnbindSizeBuilderStepImpl();
    }

    public static UnbindStep unbindHeight() {
        return new UnbindHeightBuilderStepImpl();
    }

    public static UnbindStep unbindWidth() {
        return new UnbindWidthBuilderStepImpl();
    }

    
    // BUILDER STEP INTERFACES

    static interface BindTargetStep {
        void to(Region target);
        void to(Control target);
        void to(Rectangle target);
        void to(ImageView target);
    }

    static interface BindSourceStep<A extends BindTargetStep> {
        A from(Region source);
        A from(Control source);
        A from(Rectangle source);
        A from(ImageView source);
    }

    public static interface BindWidthBuilderStep extends BindSourceStep<FromBindWidthBuilderStep> {
    }

    public static interface FromBindWidthBuilderStep extends BindTargetStep {
    }

    public static interface BindHeightBuilderStep extends BindSourceStep<FromBindHeightBuilderStep> {
    }

    public static interface FromBindHeightBuilderStep extends BindTargetStep {
    }

    public static interface BindSizeBuilderStep extends BindSourceStep<FromBindSizeBuilderStep> {
    }

    public static interface FromBindSizeBuilderStep extends BindTargetStep {
    }


    public static interface UnbindStep {
        void of(Region source);
        void of(Control source);
        void of(Rectangle source);
        void of(ImageView source);
    }


}
