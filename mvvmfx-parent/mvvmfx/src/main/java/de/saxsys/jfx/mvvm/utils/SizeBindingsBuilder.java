package de.saxsys.jfx.mvvm.utils;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;


/**
 *
 */
public class SizeBindingsBuilder {

    public static BindSizeBuilderStep bindSize(){
        return new BindSizeBuilderStep();
    }

    public static BindWidthBuilderStep bindWidth(){
        return new BindWidthBuilderStep();
    }

    public static BindHeightBuilderStep bindHeight(){
        return new BindHeightBuilderStep();
    }


    public static class BindWidthBuilderStep {

        public static class FromBindWidthBuilderStep {
            private final ReadOnlyDoubleProperty width;

            FromBindWidthBuilderStep(ReadOnlyDoubleProperty width){
                this.width = width;
            }

            public void to(Region target) {
                target.maxWidthProperty().bind(width);
                target.minWidthProperty().bind(width);
            }

            public void to(Control target) {
                target.maxWidthProperty().bind(width);
                target.minWidthProperty().bind(width);
            }

            public void to(Rectangle target) {
                target.widthProperty().bind(width);
            }

            public void to(ImageView target){
                target.fitWidthProperty().bind(width);
            }
        }

        public FromBindWidthBuilderStep from(Region source) {
            return new FromBindWidthBuilderStep(source.widthProperty());
        }

        public FromBindWidthBuilderStep from(Control source){
            return new FromBindWidthBuilderStep(source.widthProperty());
        }

        public FromBindWidthBuilderStep from(Rectangle source) {
            return new FromBindWidthBuilderStep(source.widthProperty());
        }

        public FromBindWidthBuilderStep from(ImageView source){
            return new FromBindWidthBuilderStep(source.fitWidthProperty());
        }
    }





    public static class BindHeightBuilderStep {


        public static class FromBindHeightBuilderStep {

            private final ReadOnlyDoubleProperty height;

            FromBindHeightBuilderStep(ReadOnlyDoubleProperty height){
                this.height = height;
            }

            public void to(Region target) {
                target.maxHeightProperty().bind(height);
                target.minHeightProperty().bind(height);
            }

            public void to(Control target) {
                target.maxHeightProperty().bind(height);
                target.minHeightProperty().bind(height);
            }

            public void to(Rectangle target) {
                target.heightProperty().bind(height);
            }

            public void to(ImageView target){
                target.fitHeightProperty().bind(height);
            }
        }

        public FromBindHeightBuilderStep from(Region source) {
            return new FromBindHeightBuilderStep(source.heightProperty());
        }

        public FromBindHeightBuilderStep from(Control source) {
            return new FromBindHeightBuilderStep(source.heightProperty());
        }

        public FromBindHeightBuilderStep from(Rectangle source) {
            return new FromBindHeightBuilderStep(source.heightProperty());
        }

        public FromBindHeightBuilderStep from(ImageView source){
            return new FromBindHeightBuilderStep(source.fitHeightProperty());
        }
    }


    public static class BindSizeBuilderStep {

        public static class FromBindSizeBuilderStep {

            private final ReadOnlyDoubleProperty width;
            private final ReadOnlyDoubleProperty height;

            FromBindSizeBuilderStep(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
                this.width = width;
                this.height = height;
            }

            public void to(Region target){
                target.maxWidthProperty().bind(width);
                target.minWidthProperty().bind(width);

                target.maxHeightProperty().bind(height);
                target.minHeightProperty().bind(height);
            }

            public void to(Control target) {
                target.maxWidthProperty().bind(width);
                target.minWidthProperty().bind(width);

                target.maxHeightProperty().bind(height);
                target.minHeightProperty().bind(height);
            }

            public void to(Rectangle target) {
                target.widthProperty().bind(width);
                target.heightProperty().bind(height);
            }

            public void to(ImageView target){
                target.fitWidthProperty().bind(width);
                target.fitHeightProperty().bind(height);
            }
        }


        public FromBindSizeBuilderStep from(Region source) {
            return new FromBindSizeBuilderStep(source.widthProperty(),source.heightProperty());
        }

        public FromBindSizeBuilderStep from(Control source) {
            return new FromBindSizeBuilderStep(source.widthProperty(), source.heightProperty());
        }

        public FromBindSizeBuilderStep from(Rectangle source) {
            return new FromBindSizeBuilderStep(source.widthProperty(), source.heightProperty());
        }

        public FromBindSizeBuilderStep from(ImageView source){
            return new FromBindSizeBuilderStep(source.fitWidthProperty(), source.fitHeightProperty());
        }
    }
}
