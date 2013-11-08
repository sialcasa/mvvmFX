/*******************************************************************************
 * Copyright 2013 Alexander Casall
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.jfx.mvvm.utils;

import static org.junit.Assert.assertEquals;
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

/**
 * Test whether the Bindings utils are working.
 * 
 * @author alexander.casall
 * 
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Pane.class, ScrollPane.class, Rectangle.class })
public class SizeBindingsTest {

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

	/**
	 * Bind a Pane to a Pane.
	 */
	@Test
	public void bindPaneToPane() {
		SizeBindings.bindSize(fromPaneMock, toPane);
		assertEquals(SIZEVAL, toPane.getMinWidth(), 0);
		assertEquals(SIZEVAL, toPane.getMaxWidth(), 0);
		assertEquals(SIZEVAL, toPane.getMinHeight(), 0);
		assertEquals(SIZEVAL, toPane.getMaxHeight(), 0);
	}

	/**
	 * Bind a Pane to a ScrollPane.
	 */
	@Test
	public void bindPaneToScrollPane() {
		SizeBindings.bindSize(fromPaneMock, toScrollPane);
		assertEquals(SIZEVAL, toScrollPane.getMinWidth(), 0);
		assertEquals(SIZEVAL, toScrollPane.getMaxWidth(), 0);
		assertEquals(SIZEVAL, toScrollPane.getMinHeight(), 0);
		assertEquals(SIZEVAL, toScrollPane.getMaxHeight(), 0);
	}

	/**
	 * Bind a Scrollpane to a Pane.
	 */
	@Test
	public void bindScrollPaneToPane() {
		SizeBindings.bindSize(fromScrollPaneMock, toPane);
		assertEquals(SIZEVAL, toPane.getMinWidth(), 0);
		assertEquals(SIZEVAL, toPane.getMaxWidth(), 0);
		assertEquals(SIZEVAL, toPane.getMinHeight(), 0);
		assertEquals(SIZEVAL, toPane.getMaxHeight(), 0);
	}

	/**
	 * Bind a Pane to a Rectangle.
	 */
	@Test
	public void bindPaneToRectangle() {
		SizeBindings.bindSize(fromPaneMock, toRectangle);
		assertEquals(SIZEVAL, toRectangle.getWidth(), 0);
		assertEquals(SIZEVAL, toRectangle.getHeight(), 0);
	}

	/**
	 * Bind a Rectangle to a Pane.
	 */
	@Test
	public void bindRectangleToPane() {
		SizeBindings.bindSize(fromRectangleMock, toPane);
		assertEquals(SIZEVAL, toPane.getMinWidth(), 0);
		assertEquals(SIZEVAL, toPane.getMaxWidth(), 0);
		assertEquals(SIZEVAL, toPane.getMinHeight(), 0);
		assertEquals(SIZEVAL, toPane.getMaxHeight(), 0);
	}
}
