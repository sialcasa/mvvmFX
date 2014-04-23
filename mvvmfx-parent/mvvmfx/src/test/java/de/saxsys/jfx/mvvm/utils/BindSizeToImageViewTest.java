package de.saxsys.jfx.mvvm.utils;

import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;

public class BindSizeToImageViewTest extends SizeBindingsBuilderTestBase {
    private ImageView toImageView;


    @Before
    public void setUp(){
        toImageView = new ImageView();
    }


    // SIZE BINDINGS

    @Test
    public void bindSizeFromRegionToImageView() {
        SizeBindingsBuilder.size().from(fromRegion).to(toImageView);
        assertCorrectSize(toImageView);
    }

    @Test
    public void bindSizeFromControlToImageView(){
        SizeBindingsBuilder.size().from(fromControl).to(toImageView);
        assertCorrectSize(toImageView);
    }

    @Test
    public void bindSizeFromRectangleToImageView(){
        SizeBindingsBuilder.size().from(fromRectangle).to(toImageView);
        assertCorrectSize(toImageView);
    }

    @Test
    public void bindSizeFromImageViewToImageView(){
        SizeBindingsBuilder.size().from(fromImageView).to(toImageView);
        assertCorrectSize(toImageView);
    }



    // HEIGHT Bindings
    @Test
    public void bindHeightFromRegionToImageView() {
        SizeBindingsBuilder.height().from(fromRegion).to(toImageView);
        assertCorrectHeight(toImageView);
    }

    @Test
    public void bindHeightFromControlToImageView(){
        SizeBindingsBuilder.height().from(fromControl).to(toImageView);
        assertCorrectHeight(toImageView);
    }

    @Test
    public void bindHeightFromRectangleToImageView(){
        SizeBindingsBuilder.height().from(fromRectangle).to(toImageView);
        assertCorrectHeight(toImageView);
    }

    @Test
    public void bindHeightFromImageViewToImageView(){
        SizeBindingsBuilder.height().from(fromImageView).to(toImageView);
        assertCorrectHeight(toImageView);
    }


    // WIDTH Bindings

    @Test
    public void bindWidthFromRegionToImageView() {
        SizeBindingsBuilder.width().from(fromRegion).to(toImageView);
        assertCorrectWidth(toImageView);
    }

    @Test
    public void bindWidthFromControlToImageView(){
        SizeBindingsBuilder.width().from(fromControl).to(toImageView);
        assertCorrectWidth(toImageView);
    }

    @Test
    public void bindWidthFromRectangleToImageView(){
        SizeBindingsBuilder.width().from(fromRectangle).to(toImageView);
        assertCorrectWidth(toImageView);
    }

    @Test
    public void bindWidthFromImageViewToImageView(){
        SizeBindingsBuilder.width().from(fromImageView).to(toImageView);
        assertCorrectWidth(toImageView);
    }
}
