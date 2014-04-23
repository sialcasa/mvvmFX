package de.saxsys.jfx.mvvm.utils;

import javafx.scene.layout.Region;
import org.junit.Before;
import org.junit.Test;

public class BindSizeToRegionTest extends SizeBindingsBuilderTestBase {

    private Region toRegion;


    @Before
    public void setUp() {
        toRegion = new Region();
    }

    // SIZE BINDINGS

    @Test
    public void bindSizeFromRegionToRegion() {
        SizeBindingsBuilder.size().from(fromRegion).to(toRegion);
        assertCorrectSize(toRegion);
    }

    @Test
    public void bindSizeFromControlToRegion() {
        SizeBindingsBuilder.size().from(fromControl).to(toRegion);
        assertCorrectSize(toRegion);
    }

    @Test
    public void bindSizeFromRectangleToRegion() {
        SizeBindingsBuilder.size().from(fromRectangle).to(toRegion);
        assertCorrectSize(toRegion);
    }

    @Test
    public void bindSizeFromImageViewToRegion() {
        SizeBindingsBuilder.size().from(fromImageView).to(toRegion);
        assertCorrectSize(toRegion);
    }

    // HEIGHT Bindings

    @Test
    public void bindHeightFromRegionToRegion() {
        SizeBindingsBuilder.height().from(fromRegion).to(toRegion);
        assertCorrectHeight(toRegion);
    }

    @Test
    public void bindHeightFromControlToRegion() {
        SizeBindingsBuilder.height().from(fromControl).to(toRegion);
        assertCorrectHeight(toRegion);
    }

    @Test
    public void bindHeightFromRectangleToRegion() {
        SizeBindingsBuilder.height().from(fromRectangle).to(toRegion);
        assertCorrectHeight(toRegion);
    }

    @Test
    public void bindHeightFromImageViewToRegion() {
        SizeBindingsBuilder.height().from(fromImageView).to(toRegion);
        assertCorrectHeight(toRegion);
    }

    // WIDTH Bindings

    @Test
    public void bindWidthFromRegionToRegion() {
        SizeBindingsBuilder.width().from(fromRegion).to(toRegion);
        assertCorrectWidth(toRegion);
    }

    @Test
    public void bindWidthFromControlToRegion() {
        SizeBindingsBuilder.width().from(fromControl).to(toRegion);
        assertCorrectWidth(toRegion);
    }

    @Test
    public void bindWidthFromRectangleToRegion() {
        SizeBindingsBuilder.width().from(fromRectangle).to(toRegion);
        assertCorrectWidth(toRegion);
    }

    @Test
    public void bindWidthFromImageViewToRegion() {
        SizeBindingsBuilder.width().from(fromImageView).to(toRegion);
        assertCorrectWidth(toRegion);
    }
}
