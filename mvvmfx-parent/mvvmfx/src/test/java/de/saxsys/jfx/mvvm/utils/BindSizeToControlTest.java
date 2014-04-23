package de.saxsys.jfx.mvvm.utils;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;

public class BindSizeToControlTest extends SizeBindingsBuilderTestBase {

    private Control toControl;


    @Before
    public void setUp(){
        toControl = new ScrollPane();
    }


    // SIZE BINDINGS

    @Test
    public void bindSizeFromRegionToControl() {
        SizeBindingsBuilder.size().from(fromRegion).to(toControl);
        assertCorrectSize(toControl);
    }


    @Test
    public void bindSizeFromControlToControl(){
        SizeBindingsBuilder.size().from(fromControl).to(toControl);
        assertCorrectSize(toControl);
    }


    @Test
    public void bindSizeFromRectangleToControl(){
        SizeBindingsBuilder.size().from(fromRectangle).to(toControl);
        assertCorrectSize(toControl);
    }



    // HEIGHT Bindings

    /**
     * Bind a Region to a Control.
     */
    @Test
    public void bindHeightFromRegionToControl() {
        SizeBindingsBuilder.height().from(fromRegion).to(toControl);
        assertCorrectHeight(toControl);
    }


    @Test
    public void bindHeightFromControlToControl(){
        SizeBindingsBuilder.height().from(fromControl).to(toControl);
        assertCorrectHeight(toControl);
    }

    @Test
    public void bindHeightFromRectangleToControl(){
        SizeBindingsBuilder.height().from(fromRectangle).to(toControl);
        assertCorrectHeight(toControl);
    }


    // WIDTH Bindings

    /**
     * Bind a Region to a Control.
     */
    @Test
    public void bindWidthFromRegionToControl() {
        SizeBindingsBuilder.width().from(fromRegion).to(toControl);
        assertCorrectWidth(toControl);
    }


    @Test
    public void bindWidthFromControlToControl(){
        SizeBindingsBuilder.width().from(fromControl).to(toControl);
        assertCorrectWidth(toControl);
    }

    @Test
    public void bindWidthFromRectangleToControl(){
        SizeBindingsBuilder.width().from(fromRectangle).to(toControl);
        assertCorrectWidth(toControl);
    }
}
