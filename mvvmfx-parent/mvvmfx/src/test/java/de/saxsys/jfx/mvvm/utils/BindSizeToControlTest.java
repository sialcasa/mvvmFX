package de.saxsys.jfx.mvvm.utils;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import org.junit.Before;
import org.junit.Test;

public class BindSizeToControlTest extends SizeBindingsBuilderTestBase {

    private Control toControl;


    @Before
    public void setUp() {
        toControl = new ScrollPane();
    }

    // SIZE BINDINGS

    @Test
    public void bindSizeFromRegionToControl() {
        SizeBindingsBuilder.size().from(fromRegion).to(toControl);
        assertCorrectSize(toControl);
    }

    @Test
    public void bindSizeFromControlToControl() {
        SizeBindingsBuilder.size().from(fromControl).to(toControl);
        assertCorrectSize(toControl);
    }

    @Test
    public void bindSizeFromRectangleToControl() {
        SizeBindingsBuilder.size().from(fromRectangle).to(toControl);
        assertCorrectSize(toControl);
    }

    @Test
    public void bindSizeFromImageViewToControl() {
        SizeBindingsBuilder.size().from(fromImageView).to(toControl);
        assertCorrectSize(toControl);
    }

    // HEIGHT Bindings

    @Test
    public void bindHeightFromRegionToControl() {
        SizeBindingsBuilder.height().from(fromRegion).to(toControl);
        assertCorrectHeight(toControl);
    }

    @Test
    public void bindHeightFromControlToControl() {
        SizeBindingsBuilder.height().from(fromControl).to(toControl);
        assertCorrectHeight(toControl);
    }

    @Test
    public void bindHeightFromRectangleToControl() {
        SizeBindingsBuilder.height().from(fromRectangle).to(toControl);
        assertCorrectHeight(toControl);
    }

    @Test
    public void bindHeightFromImageViewToControl() {
        SizeBindingsBuilder.height().from(fromImageView).to(toControl);
        assertCorrectHeight(toControl);
    }

    // WIDTH Bindings

    @Test
    public void bindWidthFromRegionToControl() {
        SizeBindingsBuilder.width().from(fromRegion).to(toControl);
        assertCorrectWidth(toControl);
    }

    @Test
    public void bindWidthFromControlToControl() {
        SizeBindingsBuilder.width().from(fromControl).to(toControl);
        assertCorrectWidth(toControl);
    }

    @Test
    public void bindWidthFromRectangleToControl() {
        SizeBindingsBuilder.width().from(fromRectangle).to(toControl);
        assertCorrectWidth(toControl);
    }

    @Test
    public void bindWidthFromImageViewToControl() {
        SizeBindingsBuilder.width().from(fromImageView).to(toControl);
        assertCorrectWidth(toControl);
    }
}
