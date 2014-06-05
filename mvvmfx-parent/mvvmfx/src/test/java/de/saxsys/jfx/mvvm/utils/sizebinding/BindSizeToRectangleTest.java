package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.*;

public class BindSizeToRectangleTest extends SizeBindingsBuilderTestBase {

    private Rectangle toRectangle;


    /**
     * Create elements which will bind to each other.
     */
    @Before
    public void setUp() {
        toRectangle = new Rectangle();
    }

    // SIZE BINDINGS

    @Test
    public void bindSizeFromRegionToRectangle() {
        bindSize().from(fromRegion).to(toRectangle);
        assertCorrectSize(toRectangle);
    }

    @Test
    public void bindSizeFromControlToRectangle() {
        bindSize().from(fromControl).to(toRectangle);
        assertCorrectSize(toRectangle);
    }

    @Test
    public void bindSizeFromRectangleToRectangle() {
        bindSize().from(fromRectangle).to(toRectangle);
        assertCorrectSize(toRectangle);
    }

    @Test
    public void bindSizeFromImageViewToRectangle() {
        bindSize().from(fromImageView).to(toRectangle);
        assertCorrectSize(toRectangle);
    }

    // HEIGHT Bindings

    @Test
    public void bindHeightFromRegionToRectangle() {
        bindHeight().from(fromRegion).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }

    @Test
    public void bindHeightFromControlToRectangle() {
        bindHeight().from(fromControl).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }

    @Test
    public void bindHeightFromRectangleToRectangle() {
        bindHeight().from(fromRectangle).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }

    @Test
    public void bindHeightFromImageViewToRectangle() {
        bindHeight().from(fromImageView).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }

    // WIDTH Bindings

    @Test
    public void bindWidthFromRegionToRectangle() {
        bindWidth().from(fromRegion).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }

    @Test
    public void bindWidthFromControlToRectangle() {
        bindWidth().from(fromControl).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }

    @Test
    public void bindWidthFromRectangleToRectangle() {
        bindWidth().from(fromRectangle).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }

    @Test
    public void bindWidthFromImageViewToRectangle() {
        bindWidth().from(fromImageView).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }
}
