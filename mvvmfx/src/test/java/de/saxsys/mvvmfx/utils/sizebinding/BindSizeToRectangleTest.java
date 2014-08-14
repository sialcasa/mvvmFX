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
package de.saxsys.mvvmfx.utils.sizebinding;

import static de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder.bindHeight;
import static de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder.bindSize;
import static de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder.bindWidth;

import javafx.scene.shape.Rectangle;

import org.junit.Before;
import org.junit.Test;

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
