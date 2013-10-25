package de.saxsys.jfx.mvvm.utils;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test whether the Bindings utils are working.
 * 
 * @author alexander.casall
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Pane.class, ScrollPane.class, Rectangle.class })
public class UFXBindingsTest {

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
        PowerMockito.when(fromPaneMock.widthProperty()).thenReturn(new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromRectangleMock.widthProperty()).thenReturn(new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromScrollPaneMock.widthProperty()).thenReturn(new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromPaneMock.heightProperty()).thenReturn(new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromRectangleMock.heightProperty()).thenReturn(new SimpleDoubleProperty(SIZEVAL));
        PowerMockito.when(fromScrollPaneMock.heightProperty()).thenReturn(new SimpleDoubleProperty(SIZEVAL));

        toPane = new Pane();
        toScrollPane = new ScrollPane();
        toRectangle = new Rectangle();
    }

    /**
     * Bind a Pane to a Pane.
     */
    @Test
    public void bindPaneToPane() {
        UFXBindings.bindSize(fromPaneMock, toPane);
        assertEquals(SIZEVAL, toPane.getMinWidth(),0);
        assertEquals(SIZEVAL, toPane.getMaxWidth(),0);
        assertEquals(SIZEVAL, toPane.getMinHeight(),0);
        assertEquals(SIZEVAL, toPane.getMaxHeight(),0);
    }

    /**
     * Bind a Pane to a ScrollPane.
     */
    @Test
    public void bindPaneToScrollPane() {
        UFXBindings.bindSize(fromPaneMock, toScrollPane);
        assertEquals(SIZEVAL, toScrollPane.getMinWidth(),0);
        assertEquals(SIZEVAL, toScrollPane.getMaxWidth(),0);
        assertEquals(SIZEVAL, toScrollPane.getMinHeight(),0);
        assertEquals(SIZEVAL, toScrollPane.getMaxHeight(),0);
    }

    /**
     * Bind a Scrollpane to a Pane.
     */
    @Test
    public void bindScrollPaneToPane() {
        UFXBindings.bindSize(fromScrollPaneMock, toPane);
        assertEquals(SIZEVAL, toPane.getMinWidth(),0);
        assertEquals(SIZEVAL, toPane.getMaxWidth(),0);
        assertEquals(SIZEVAL, toPane.getMinHeight(),0);
        assertEquals(SIZEVAL, toPane.getMaxHeight(),0);
    }

    /**
     * Bind a Pane to a Rectangle.
     */
    @Test
    public void bindPaneToRectangle() {
        UFXBindings.bindSize(fromPaneMock, toRectangle);
        assertEquals(SIZEVAL, toRectangle.getWidth(),0);
        assertEquals(SIZEVAL, toRectangle.getHeight(),0);
    }

    /**
     * Bind a Rectangle to a Pane.
     */
    @Test
    public void bindRectangleToPane() {
        UFXBindings.bindSize(fromRectangleMock, toPane);
        assertEquals(SIZEVAL, toPane.getMinWidth(),0);
        assertEquals(SIZEVAL, toPane.getMaxWidth(),0);
        assertEquals(SIZEVAL, toPane.getMinHeight(),0);
        assertEquals(SIZEVAL, toPane.getMaxHeight(),0);
    }

    /**
     * Check whether a node has a parent.
     */
    @Test
    public void hasNodeAParent() {
        // Truefall im Unittest nicht testbar
        assertEquals(false, UFXBindings.hasParent(fromRectangleMock));
    }

}