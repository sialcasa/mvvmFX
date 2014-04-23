package de.saxsys.jfx.mvvm.utils;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;

public class BindSizeToRectangleTest extends SizeBindingsBuilderTestBase {

    private Rectangle toRectangle;


    /**
     * Create elements which will bind to each other.
     */
    @Before
    public void setUp(){
        toRectangle = new Rectangle();
    }


    // SIZE BINDINGS

    @Test
    public void bindSizeFromRegionToRectangle() {
        SizeBindingsBuilder.size().from(fromRegion).to(toRectangle);
        assertCorrectSize(toRectangle);
    }


    @Test
    public void bindSizeFromControlToRectangle(){
        SizeBindingsBuilder.size().from(fromControl).to(toRectangle);
        assertCorrectSize(toRectangle);
    }

    @Test
    public void bindSizeFromRectangleToRectangle(){
        SizeBindingsBuilder.size().from(fromRectangle).to(toRectangle);
        assertCorrectSize(toRectangle);
    }




    // HEIGHT Bindings

    /**
     * Bind a Region to a Rectangle.
     */
    @Test
    public void bindHeightFromRegionToRectangle() {
        SizeBindingsBuilder.height().from(fromRegion).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }

    @Test
    public void bindHeightFromControlToRectangle(){
        SizeBindingsBuilder.height().from(fromControl).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }


    @Test
    public void bindHeightFromRectangleToRectangle(){
        SizeBindingsBuilder.height().from(fromRectangle).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }



    // WIDTH Bindings


    /**
     * Bind a Region to a Rectangle.
     */
    @Test
    public void bindWidthFromRegionToRectangle() {
        SizeBindingsBuilder.width().from(fromRegion).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }


    @Test
    public void bindWidthFromControlToRectangle(){
        SizeBindingsBuilder.width().from(fromControl).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }

    @Test
    public void bindWidthFromRectangleToRectangle(){
        SizeBindingsBuilder.width().from(fromRectangle).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }
}
