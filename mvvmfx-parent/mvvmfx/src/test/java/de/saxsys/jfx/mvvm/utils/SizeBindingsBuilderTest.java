package de.saxsys.jfx.mvvm.utils;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Pane.class, ScrollPane.class, Rectangle.class })
public class SizeBindingsBuilderTest {

    private static final double SIZEVAL = 100d;
    private Pane fromPaneMock;
    private Pane toPane;
    private ScrollPane fromScrollPaneMock;
    private ScrollPane toScrollPane;
    private Rectangle fromRectangleMock;
    private Rectangle toRectangle;


    /**
     * Create elements which will bind to each other.
     */
    @Before
    public void setUp() {
        fromPaneMock = PowerMockito.mock(Pane.class);
        fromScrollPaneMock = PowerMockito.mock(ScrollPane.class);
        fromRectangleMock = PowerMockito.mock(Rectangle.class);

        PowerMockito.when(fromPaneMock.getWidth()).thenReturn(SIZEVAL);
        PowerMockito.when(fromRectangleMock.getWidth()).thenReturn(SIZEVAL);
        PowerMockito.when(fromScrollPaneMock.getWidth()).thenReturn(SIZEVAL);
        PowerMockito.when(fromPaneMock.widthProperty()).thenReturn(
            new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromRectangleMock.widthProperty()).thenReturn(
            new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromScrollPaneMock.widthProperty()).thenReturn(
            new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromPaneMock.heightProperty()).thenReturn(
            new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromRectangleMock.heightProperty()).thenReturn(
            new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromScrollPaneMock.heightProperty()).thenReturn(
            new SimpleDoubleProperty(SIZEVAL));

        toPane = new Pane();
        toScrollPane = new ScrollPane();
        toRectangle = new Rectangle();
    }


    // SIZE BINDINGS


    @Test
    public void bindSizeFromPaneToPane(){
        SizeBindingsBuilder.size().from(fromPaneMock).to(toPane);
        assertCorrectSize(toPane);
    }

    /**
     * Bind a Pane to a ScrollPane.
     */
    @Test
    public void bindSizeFromPaneToScrollPane() {
        SizeBindingsBuilder.size().from(fromPaneMock).to(toScrollPane);
        assertCorrectSize(toScrollPane);
    }

    /**
     * Bind a Pane to a Rectangle.
     */
    @Test
    public void bindSizeFromPaneToRectangle() {
        SizeBindingsBuilder.size().from(fromPaneMock).to(toRectangle);
        assertCorrectSize(toRectangle);
    }





    /**
     * Bind a Scrollpane to a Pane.
     */
    @Test
    public void bindSizeFromScrollPaneToPane() {
        SizeBindingsBuilder.size().from(fromScrollPaneMock).to(toPane);
        assertCorrectSize(toPane);
    }


    @Test
    public void bindSizeFromScrollPaneToScrollPane(){
        SizeBindingsBuilder.size().from(fromScrollPaneMock).to(toScrollPane);
        assertCorrectSize(toScrollPane);
    }

    @Test
    public void bindSizeFromScrollPaneToRectangle(){
        SizeBindingsBuilder.size().from(fromScrollPaneMock).to(toRectangle);
        assertCorrectSize(toRectangle);
    }


    /**
     * Bind a Rectangle to a Pane.
     */
    @Test
    public void bindSizeFromRectangleToPane() {
        SizeBindingsBuilder.size().from(fromRectangleMock).to(toPane);
        assertCorrectSize(toPane);
    }

    @Test
    public void bindSizeFromRectangleToScrollPane(){
        SizeBindingsBuilder.size().from(fromRectangleMock).to(toScrollPane);
        assertCorrectSize(toScrollPane);
    }

    @Test
    public void bindSizeFromRectangleToRectangle(){
        SizeBindingsBuilder.size().from(fromRectangleMock).to(toRectangle);
        assertCorrectSize(toRectangle);
    }




    // HEIGHT Bindings

    @Test
    public void bindHeightFromPaneToPane(){
        SizeBindingsBuilder.height().from(fromPaneMock).to(toPane);
        assertCorrectHeight(toPane);
    }

    /**
     * Bind a Pane to a ScrollPane.
     */
    @Test
    public void bindHeightFromPaneToScrollPane() {
        SizeBindingsBuilder.height().from(fromPaneMock).to(toScrollPane);
        assertCorrectHeight(toScrollPane);
    }

    /**
     * Bind a Pane to a Rectangle.
     */
    @Test
    public void bindHeightFromPaneToRectangle() {
        SizeBindingsBuilder.height().from(fromPaneMock).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }





    /**
     * Bind a Scrollpane to a Pane.
     */
    @Test
    public void bindHeightFromScrollPaneToPane() {
        SizeBindingsBuilder.height().from(fromScrollPaneMock).to(toPane);
        assertCorrectHeight(toPane);
    }


    @Test
    public void bindHeightFromScrollPaneToScrollPane(){
        SizeBindingsBuilder.height().from(fromScrollPaneMock).to(toScrollPane);
        assertCorrectHeight(toScrollPane);
    }

    @Test
    public void bindHeightFromScrollPaneToRectangle(){
        SizeBindingsBuilder.height().from(fromScrollPaneMock).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }


    /**
     * Bind a Rectangle to a Pane.
     */
    @Test
    public void bindHeightFromRectangleToPane() {
        SizeBindingsBuilder.height().from(fromRectangleMock).to(toPane);
        assertCorrectHeight(toPane);
    }

    @Test
    public void bindHeightFromRectangleToScrollPane(){
        SizeBindingsBuilder.height().from(fromRectangleMock).to(toScrollPane);
        assertCorrectHeight(toScrollPane);
    }

    @Test
    public void bindHeightFromRectangleToRectangle(){
        SizeBindingsBuilder.height().from(fromRectangleMock).to(toRectangle);
        assertCorrectHeight(toRectangle);
    }



    // WIDTH Bindings

    @Test
    public void bindWidthFromPaneToPane(){
        SizeBindingsBuilder.width().from(fromPaneMock).to(toPane);
        assertCorrectWidth(toPane);
    }

    /**
     * Bind a Pane to a ScrollPane.
     */
    @Test
    public void bindWidthFromPaneToScrollPane() {
        SizeBindingsBuilder.width().from(fromPaneMock).to(toScrollPane);
        assertCorrectWidth(toScrollPane);
    }

    /**
     * Bind a Pane to a Rectangle.
     */
    @Test
    public void bindWidthFromPaneToRectangle() {
        SizeBindingsBuilder.width().from(fromPaneMock).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }





    /**
     * Bind a Scrollpane to a Pane.
     */
    @Test
    public void bindWidthFromScrollPaneToPane() {
        SizeBindingsBuilder.width().from(fromScrollPaneMock).to(toPane);
        assertCorrectWidth(toPane);
    }


    @Test
    public void bindWidthFromScrollPaneToScrollPane(){
        SizeBindingsBuilder.width().from(fromScrollPaneMock).to(toScrollPane);
        assertCorrectWidth(toScrollPane);
    }

    @Test
    public void bindWidthFromScrollPaneToRectangle(){
        SizeBindingsBuilder.width().from(fromScrollPaneMock).to(toRectangle);
        assertCorrectWidth(toRectangle);
    }


    /**
     * Bind a Rectangle to a Pane.
     */
    @Test
    public void bindWidthFromRectangleToPane() {
        SizeBindingsBuilder.width().from(fromRectangleMock).to(toPane);
        assertCorrectWidth(toPane);
    }

    @Test
    public void bindWidthFromRectangleToScrollPane(){
        SizeBindingsBuilder.width().from(fromRectangleMock).to(toScrollPane);
        assertCorrectWidth(toScrollPane);
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


    private void assertCorrectSize(ScrollPane scrollPane){
        assertCorrectHeight(scrollPane);
        assertCorrectWidth(scrollPane);
    }

    private void assertCorrectSize(Pane pane){
        assertCorrectHeight(pane);
        assertCorrectWidth(pane);
    }


    private void assertCorrectHeight(Rectangle rectangle){
        assertThat(rectangle.getHeight()).isEqualTo(SIZEVAL);
    }


    private void assertCorrectHeight(ScrollPane scrollPane){
        assertThat(scrollPane.getMaxHeight()).isEqualTo(SIZEVAL);
        assertThat(scrollPane.getMinHeight()).isEqualTo(SIZEVAL);
    }

    private void assertCorrectHeight(Pane pane){
        assertThat(pane.getMaxHeight()).isEqualTo(SIZEVAL);
        assertThat(pane.getMinHeight()).isEqualTo(SIZEVAL);
    }


    private void assertCorrectWidth(Rectangle rectangle){
        assertThat(rectangle.getWidth()).isEqualTo(SIZEVAL);
    }


    private void assertCorrectWidth(ScrollPane scrollPane){
        assertThat(scrollPane.getMaxWidth()).isEqualTo(SIZEVAL);
        assertThat(scrollPane.getMinWidth()).isEqualTo(SIZEVAL);
    }

    private void assertCorrectWidth(Pane pane){
        assertThat(pane.getMaxWidth()).isEqualTo(SIZEVAL);
        assertThat(pane.getMinWidth()).isEqualTo(SIZEVAL);
    }
}
