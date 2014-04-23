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

    public static SizeBuilderStep size(){
        return new SizeBuilderStep();
    }

    public static WidthBuilderStep width(){
        return new WidthBuilderStep();
    }

    public static HeightBuilderStep height(){
        return new HeightBuilderStep();
    }

    public static class WidthBuilderStep {

        public static class FromWidthBuilderStep {
            private final ReadOnlyDoubleProperty width;

            FromWidthBuilderStep(ReadOnlyDoubleProperty width){
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

        public FromWidthBuilderStep from(Region source) {
            return new FromWidthBuilderStep(source.widthProperty());
        }

        public FromWidthBuilderStep from(Control source){
            return new FromWidthBuilderStep(source.widthProperty());
        }

        public FromWidthBuilderStep from(Rectangle source) {
            return new FromWidthBuilderStep(source.widthProperty());
        }

        public FromWidthBuilderStep from(ImageView source){
            return new FromWidthBuilderStep(source.fitWidthProperty());
        }
    }





    public static class HeightBuilderStep {


        public static class FromHeightBuilderStep {

            private final ReadOnlyDoubleProperty height;

            FromHeightBuilderStep(ReadOnlyDoubleProperty height){
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

        public FromHeightBuilderStep from(Region source) {
            return new FromHeightBuilderStep(source.heightProperty());
        }

        public FromHeightBuilderStep from(Control source) {
            return new FromHeightBuilderStep(source.heightProperty());
        }

        public FromHeightBuilderStep from(Rectangle source) {
            return new FromHeightBuilderStep(source.heightProperty());
        }

        public FromHeightBuilderStep from(ImageView source){
            return new FromHeightBuilderStep(source.fitHeightProperty());
        }
    }


    public static class SizeBuilderStep {

        public static class FromSizeBuilderStep {

            private final ReadOnlyDoubleProperty width;
            private final ReadOnlyDoubleProperty height;

            FromSizeBuilderStep(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
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


        public FromSizeBuilderStep from(Region source) {
            return new FromSizeBuilderStep(source.widthProperty(),source.heightProperty());
        }

        public FromSizeBuilderStep from(Control source) {
            return new FromSizeBuilderStep(source.widthProperty(), source.heightProperty());
        }

        public FromSizeBuilderStep from(Rectangle source) {
            return new FromSizeBuilderStep(source.widthProperty(), source.heightProperty());
        }

        public FromSizeBuilderStep from(ImageView source){
            return new FromSizeBuilderStep(source.fitWidthProperty(), source.fitHeightProperty());
        }
    }
}
