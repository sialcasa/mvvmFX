package de.saxsys.jfx.mvvm.utils;

import de.saxsys.jfx.mvvm.JavaFXThreadingRule;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.assertj.core.api.Assertions.*;

public class SizeBindingsBuilderTest{

    private static final double SIZEVAL = 100d;
    private Pane fromPaneMock;
    private Pane toPane;
    private ScrollPane fromScrollPaneMock;
    private ScrollPane toScrollPane;
    private Rectangle fromRectangleMock;
    private Rectangle toRectangle;


    @Rule
    public JavaFXThreadingRule rule = new JavaFXThreadingRule();

    /**
     * Create elements which will bind to each other.
     */
    @Before
    public void setUp(){
        fromPaneMock = new Pane();
        mockSize(fromPaneMock);
        fromScrollPaneMock = new ScrollPane();
        mockSize(fromScrollPaneMock);
        fromRectangleMock = new Rectangle();
        mockSize(fromRectangleMock);

        toPane = new Pane();
        toScrollPane = new ScrollPane();
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
