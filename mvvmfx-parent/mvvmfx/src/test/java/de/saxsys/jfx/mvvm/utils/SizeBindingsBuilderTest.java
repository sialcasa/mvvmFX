package de.saxsys.jfx.mvvm.utils;

import de.saxsys.jfx.mvvm.JavaFXThreadingRule;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.assertj.core.api.Assertions.*;

public class SizeBindingsBuilderTest{

    private static final double SIZEVAL = 100d;
    private Region fromRegionMock;
    private Region toRegion;
    private Control fromControlMock;
    private Control toControl;
    private Rectangle fromRectangleMock;
    private Rectangle toRectangle;


    @Rule
    public JavaFXThreadingRule rule = new JavaFXThreadingRule();

    /**
     * Create elements which will bind to each other.
     */
    @Before
    public void setUp(){
        fromRegionMock = new Region();
        mockSize(fromRegionMock);
        fromControlMock = new ScrollPane();
        mockSize(fromControlMock);
        fromRectangleMock = new Rectangle();
        mockSize(fromRectangleMock);

        toRegion = new Region();
        toControl = new ScrollPane();
        toRectangle = new Rectangle();
    }

    /**
     * Mock the internal storage of the width and height. This is a workaround because we
     * can't use PowerMock in this case due to conflicts with Javafx 8 initialization of controls.
     */
    private void mockSize(Object object){
        Whitebox.setInternalState(object, "width", new ReadOnlyDoubleWrapper(SIZEVAL));
        Whitebox.setInternalState(object, "height", new ReadOnlyDoubleWrapper(SIZEVAL));
    }


    // SIZE BINDINGS


    @Test
    public void bindSizeFromRegionToRegion(){
        SizeBindingsBuilder.size().from(fromRegionMock).to(toRegion);
        assertCorrectSize(toRegion);
    }

    @Test
    public void bindSizeFromRegionToControl() {
        SizeBindingsBuilder.size().from(fromRegionMock).to(toControl);
        assertCorrectSize(toControl);
    }

    @Test
    public void bindSizeFromRegionToRectangle() {
        SizeBindingsBuilder.size().from(fromRegionMock).to(toRectangle);
        assertCorrectSize(toRectangle);
    }

    @Test
    public void bindSizeFromControlToRegion() {
        SizeBindingsBuilder.size().from(fromControlMock).to(toRegion);
        assertCorrectSize(toRegion);
    }


    @Test
    public void bindSizeFromControlToControl(){
        SizeBindingsBuilder.size().from(fromControlMock).to(toControl);
        assertCorrectSize(toControl);
    }

    @Test
    public void bindSizeFromControlToRectangle(){
        SizeBindingsBuilder.size().from(fromControlMock).to(toRectangle);
        assertCorrectSize(toRectangle);
    }

    @Test
    public void bindSizeFromRectangleToRegion() {
        SizeBindingsBuilder.size().from(fromRectangleMock).to(toRegion);
        assertCorrectSize(toRegion);
    }

    @Test
    public void bindSizeFromRectangleToControl(){
        SizeBindingsBuilder.size().from(fromRectangleMock).to(toControl);
        assertCorrectSize(toControl);
    }

    @Test
    public void bindSizeFromRectangleToRectangle(){
        SizeBindingsBuilder.size().from(fromRectangleMock).to(toRectangle);
        assertCorrectSize(toRectangle);
    }




    // HEIGHT Bindings

    @Test
    public void bindHeightFromRegionToRegion(){
        SizeBindingsBuilder.height().from(fromRegionMock).to(toRegion);
        assertCorrectHeight(toRegion);
    }

    /**
     * Bind a Region to a Control.
     */
    @Test
    public void bindHeightFromRegionToControl() {
        SizeBindingsBuilder.height().from(fromRegionMock).to(toControl);
        assertCorrectHeight(toControl);
    }

    /**
     * Bind a Region to a Rectangle.
     */
    @Test
    public void bindHeightFromRegionToRectangle() {
        SizeBindingsBuilder.height().from(fromRegionMock).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }





    /**
     * Bind a Control to a Region.
     */
    @Test
    public void bindHeightFromControlToRegion() {
        SizeBindingsBuilder.height().from(fromControlMock).to(toRegion);
        assertCorrectHeight(toRegion);
    }


    @Test
    public void bindHeightFromControlToControl(){
        SizeBindingsBuilder.height().from(fromControlMock).to(toControl);
        assertCorrectHeight(toControl);
    }

    @Test
    public void bindHeightFromControlToRectangle(){
        SizeBindingsBuilder.height().from(fromControlMock).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }


    /**
     * Bind a Rectangle to a Region.
     */
    @Test
    public void bindHeightFromRectangleToRegion() {
        SizeBindingsBuilder.height().from(fromRectangleMock).to(toRegion);
        assertCorrectHeight(toRegion);
    }

    @Test
    public void bindHeightFromRectangleToControl(){
        SizeBindingsBuilder.height().from(fromRectangleMock).to(toControl);
        assertCorrectHeight(toControl);
    }

    @Test
    public void bindHeightFromRectangleToRectangle(){
        SizeBindingsBuilder.height().from(fromRectangleMock).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }



    // WIDTH Bindings

    @Test
    public void bindWidthFromRegionToRegion(){
        SizeBindingsBuilder.width().from(fromRegionMock).to(toRegion);
        assertCorrectWidth(toRegion);
    }

    /**
     * Bind a Region to a Control.
     */
    @Test
    public void bindWidthFromRegionToControl() {
        SizeBindingsBuilder.width().from(fromRegionMock).to(toControl);
        assertCorrectWidth(toControl);
    }

    /**
     * Bind a Region to a Rectangle.
     */
    @Test
    public void bindWidthFromRegionToRectangle() {
        SizeBindingsBuilder.width().from(fromRegionMock).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }





    /**
     * Bind a Control to a Region.
     */
    @Test
    public void bindWidthFromControlToRegion() {
        SizeBindingsBuilder.width().from(fromControlMock).to(toRegion);
        assertCorrectWidth(toRegion);
    }


    @Test
    public void bindWidthFromControlToControl(){
        SizeBindingsBuilder.width().from(fromControlMock).to(toControl);
        assertCorrectWidth(toControl);
    }

    @Test
    public void bindWidthFromControlToRectangle(){
        SizeBindingsBuilder.width().from(fromControlMock).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }


    /**
     * Bind a Rectangle to a Region.
     */
    @Test
    public void bindWidthFromRectangleToRegion() {
        SizeBindingsBuilder.width().from(fromRectangleMock).to(toRegion);
        assertCorrectWidth(toRegion);
    }

    @Test
    public void bindWidthFromRectangleToControl(){
        SizeBindingsBuilder.width().from(fromRectangleMock).to(toControl);
        assertCorrectWidth(toControl);
    }

    @Test
    public void bindWidthFromRectangleToRectangle(){
        SizeBindingsBuilder.width().from(fromRectangleMock).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }








    private void assertCorrectSize(Rectangle rectangle){
        assertCorrectHeight(rectangle);
        assertCorrectWidth(rectangle);
    }


    private void assertCorrectSize(Control control){
        assertCorrectHeight(control);
        assertCorrectWidth(control);
    }

    private void assertCorrectSize(Region region){
        assertCorrectHeight(region);
        assertCorrectWidth(region);
    }


    private void assertCorrectHeight(Rectangle rectangle){
        assertThat(rectangle.getHeight()).isEqualTo(SIZEVAL);
    }


    private void assertCorrectHeight(Control control){
        assertThat(control.getMaxHeight()).isEqualTo(SIZEVAL);
        assertThat(control.getMinHeight()).isEqualTo(SIZEVAL);
    }

    private void assertCorrectHeight(Region region){
        assertThat(region.getMaxHeight()).isEqualTo(SIZEVAL);
        assertThat(region.getMinHeight()).isEqualTo(SIZEVAL);
    }


    private void assertCorrectWidth(Rectangle rectangle){
        assertThat(rectangle.getWidth()).isEqualTo(SIZEVAL);
    }


    private void assertCorrectWidth(Control control){
        assertThat(control.getMaxWidth()).isEqualTo(SIZEVAL);
        assertThat(control.getMinWidth()).isEqualTo(SIZEVAL);
    }

    private void assertCorrectWidth(Region region){
        assertThat(region.getMaxWidth()).isEqualTo(SIZEVAL);
        assertThat(region.getMinWidth()).isEqualTo(SIZEVAL);
    }
}
