/*******************************************************************************
 * Copyright 2014 Manuel Mauky
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
package de.saxsys.jfx.mvvm.utils.sizebinding;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindHeight;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindSize;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindWidth;

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
		bindSize().from(fromRegion).to(toControl);
		assertCorrectSize(toControl);
	}
	
	@Test
	public void bindSizeFromControlToControl() {
		bindSize().from(fromControl).to(toControl);
		assertCorrectSize(toControl);
	}
	
	@Test
	public void bindSizeFromRectangleToControl() {
		bindSize().from(fromRectangle).to(toControl);
		assertCorrectSize(toControl);
	}
	
	@Test
	public void bindSizeFromImageViewToControl() {
		bindSize().from(fromImageView).to(toControl);
		assertCorrectSize(toControl);
	}
	
	// HEIGHT Bindings
	
	@Test
	public void bindHeightFromRegionToControl() {
		bindHeight().from(fromRegion).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	@Test
	public void bindHeightFromControlToControl() {
		bindHeight().from(fromControl).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	@Test
	public void bindHeightFromRectangleToControl() {
		bindHeight().from(fromRectangle).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	@Test
	public void bindHeightFromImageViewToControl() {
		bindHeight().from(fromImageView).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	// WIDTH Bindings
	
	@Test
	public void bindWidthFromRegionToControl() {
		bindWidth().from(fromRegion).to(toControl);
		assertCorrectWidth(toControl);
	}
	
	@Test
	public void bindWidthFromControlToControl() {
		bindWidth().from(fromControl).to(toControl);
		assertCorrectWidth(toControl);
	}
	
	@Test
	public void bindWidthFromRectangleToControl() {
		bindWidth().from(fromRectangle).to(toControl);
		assertCorrectWidth(toControl);
	}
	
	@Test
	public void bindWidthFromImageViewToControl() {
		bindWidth().from(fromImageView).to(toControl);
		assertCorrectWidth(toControl);
	}
}
