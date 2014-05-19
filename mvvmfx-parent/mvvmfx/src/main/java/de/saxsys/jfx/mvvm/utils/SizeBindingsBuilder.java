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
        return new BindSizeBuilderStepImpl();
    }
    
    public static UnbindSizeBuilderStep unbindSize() {
        return new UnbindSizeBuilderStep();
    }

    public static BindWidthBuilderStep bindWidth(){
        return new BindWidthBuilderStepImpl();
    }

    public static BindHeightBuilderStep bindHeight(){
        return new BindHeightBuilderStepImpl();
    }


    static interface TargetStep {
        void to(Region target);
        void to(Control target);
        void to(Rectangle target);
        void to(ImageView target);
    }
    
    static interface SourceStep<A extends TargetStep> {
        A from(Region source);
        A from(Control source);
        A from(Rectangle source);
        A from(ImageView source);
    }

    public static interface BindWidthBuilderStep extends SourceStep<FromBindWidthBuilderStep> {
    }

    public static interface FromBindWidthBuilderStep extends TargetStep {
    }

    public static interface BindHeightBuilderStep extends SourceStep<FromBindHeightBuilderStep> {
    }

    public static interface FromBindHeightBuilderStep extends TargetStep {
    }

    public static interface BindSizeBuilderStep extends SourceStep<FromBindSizeBuilderStep> {
    }

    public static interface FromBindSizeBuilderStep extends TargetStep {
    }
    


    public static class BindWidthBuilderStepImpl implements BindWidthBuilderStep, FromBindWidthBuilderStep{

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
        public FromBindWidthBuilderStep from(Region source) {
            width = source.widthProperty();
            return this;
        }
        @Override
        public FromBindWidthBuilderStep from(Control source){
            width = source.widthProperty();
            return this;
        }
        @Override
        public FromBindWidthBuilderStep from(Rectangle source) {
            width = source.widthProperty();
            return this;
        }
        @Override
        public FromBindWidthBuilderStep from(ImageView source){
            width = source.fitWidthProperty();
            return this;
        }
    }
     

    public static class BindHeightBuilderStepImpl implements BindHeightBuilderStep, FromBindHeightBuilderStep{

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
        public FromBindHeightBuilderStep from(Region source) {
            height = source.heightProperty();
            return this;
        }
        @Override
        public FromBindHeightBuilderStep from(Control source) {
            height = source.heightProperty();
            return this;
        }
        @Override
        public FromBindHeightBuilderStep from(Rectangle source) {
            height = source.heightProperty();
            return this;
        }
        @Override
        public FromBindHeightBuilderStep from(ImageView source){
            height = source.fitHeightProperty();
            return this;
        }
    }

    

    public static class BindSizeBuilderStepImpl implements BindSizeBuilderStep, FromBindSizeBuilderStep {
        private FromBindWidthBuilderStep widthStep;
        private FromBindHeightBuilderStep heightStep;

        @Override
        public void to(Region target){
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
        public void to(ImageView target){
            widthStep.to(target);
            heightStep.to(target);
        }

        @Override
        public FromBindSizeBuilderStep from(Region source) {
            widthStep = bindWidth().from(source);
            heightStep = bindHeight().from(source);
            return this;
        }
        
        @Override
        public FromBindSizeBuilderStep from(Control source) {
            widthStep = bindWidth().from(source);
            heightStep = bindHeight().from(source);
            return this;
        }
        
        @Override
        public FromBindSizeBuilderStep from(Rectangle source) {
            widthStep = bindWidth().from(source);
            heightStep = bindHeight().from(source);
            return this;
        }
        
        @Override
        public FromBindSizeBuilderStep from(ImageView source){
            widthStep = bindWidth().from(source);
            heightStep = bindHeight().from(source);
            return this;
        }
    }

    public static class UnbindSizeBuilderStep {
        public void of(Region source){
            source.maxWidthProperty().unbind();
            source.minWidthProperty().unbind();

            source.maxHeightProperty().unbind();
            source.minHeightProperty().unbind();
        }
        
        public void of(Control source){
            source.maxWidthProperty().unbind();
            source.minWidthProperty().unbind();

            source.maxHeightProperty().unbind();
            source.minHeightProperty().unbind();
        }
        
        public void of(ImageView source){
            source.fitWidthProperty().unbind();
            source.fitHeightProperty().unbind();
        }
        
        public void of(Rectangle source) {
            source.widthProperty().unbind();
            source.heightProperty().unbind();
        }
    }
}
