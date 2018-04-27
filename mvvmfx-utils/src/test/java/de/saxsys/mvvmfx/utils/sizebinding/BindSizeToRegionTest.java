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

import static de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder.*;

import javafx.scene.layout.Region;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BindSizeToRegionTest extends SizeBindingsBuilderTestBase {
	
	private Region toRegion;
	
	
	@BeforeEach
	public void setUp() {
		toRegion = new Region();
	}
	
	// SIZE BINDINGS
	
	@Test
	public void bindSizeFromRegionToRegion() {
		bindSize().from(fromRegion).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	@Test
	public void bindSizeFromControlToRegion() {
		bindSize().from(fromControl).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	@Test
	public void bindSizeFromRectangleToRegion() {
		bindSize().from(fromRectangle).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	@Test
	public void bindSizeFromImageViewToRegion() {
		bindSize().from(fromImageView).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	// HEIGHT Bindings
	
	@Test
	public void bindHeightFromRegionToRegion() {
		bindHeight().from(fromRegion).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	@Test
	public void bindHeightFromControlToRegion() {
		bindHeight().from(fromControl).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	@Test
	public void bindHeightFromRectangleToRegion() {
		bindHeight().from(fromRectangle).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	@Test
	public void bindHeightFromImageViewToRegion() {
		bindHeight().from(fromImageView).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	// WIDTH Bindings
	
	@Test
	public void bindWidthFromRegionToRegion() {
		bindWidth().from(fromRegion).to(toRegion);
		assertCorrectWidth(toRegion);
	}
	
	@Test
	public void bindWidthFromControlToRegion() {
		bindWidth().from(fromControl).to(toRegion);
		assertCorrectWidth(toRegion);
	}
	
	@Test
	public void bindWidthFromRectangleToRegion() {
		bindWidth().from(fromRectangle).to(toRegion);
		assertCorrectWidth(toRegion);
	}
	
	@Test
	public void bindWidthFromImageViewToRegion() {
		bindWidth().from(fromImageView).to(toRegion);
		assertCorrectWidth(toRegion);
	}
}
