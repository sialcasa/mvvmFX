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

import javafx.scene.image.ImageView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BindSizeToImageViewTest extends SizeBindingsBuilderTestBase {
	private ImageView toImageView;
	
	
	@BeforeEach
	public void setUp() {
		toImageView = new ImageView();
	}
	
	
	// SIZE BINDINGS
	
	@Test
	public void bindSizeFromRegionToImageView() {
		bindSize().from(fromRegion).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	@Test
	public void bindSizeFromControlToImageView() {
		bindSize().from(fromControl).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	@Test
	public void bindSizeFromRectangleToImageView() {
		bindSize().from(fromRectangle).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	@Test
	public void bindSizeFromImageViewToImageView() {
		bindSize().from(fromImageView).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	
	
	// HEIGHT Bindings
	@Test
	public void bindHeightFromRegionToImageView() {
		bindHeight().from(fromRegion).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	@Test
	public void bindHeightFromControlToImageView() {
		bindHeight().from(fromControl).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	@Test
	public void bindHeightFromRectangleToImageView() {
		bindHeight().from(fromRectangle).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	@Test
	public void bindHeightFromImageViewToImageView() {
		bindHeight().from(fromImageView).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	
	// WIDTH Bindings
	
	@Test
	public void bindWidthFromRegionToImageView() {
		bindWidth().from(fromRegion).to(toImageView);
		assertCorrectWidth(toImageView);
	}
	
	@Test
	public void bindWidthFromControlToImageView() {
		bindWidth().from(fromControl).to(toImageView);
		assertCorrectWidth(toImageView);
	}
	
	@Test
	public void bindWidthFromRectangleToImageView() {
		bindWidth().from(fromRectangle).to(toImageView);
		assertCorrectWidth(toImageView);
	}
	
	@Test
	public void bindWidthFromImageViewToImageView() {
		bindWidth().from(fromImageView).to(toImageView);
		assertCorrectWidth(toImageView);
	}
}
