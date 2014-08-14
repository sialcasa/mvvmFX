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

import static de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder.bindSize;
import static de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder.unbindSize;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import org.junit.Before;
import org.junit.Test;


public class UnbindSizeTest extends SizeBindingsBuilderTestBase {
	
	private ImageView targetImageView;
	private Control targetControl;
	private Rectangle targetRectangle;
	private Region targetRegion;
	
	
	@Before
	public void setUp() {
		targetImageView = new ImageView();
		targetControl = new ScrollPane();
		targetRectangle = new Rectangle();
		targetRegion = new Region();
	}
	
	@Test
	public void unbindSizeOfImageView() {
		bindSize().from(fromRectangle).to(targetImageView);
		
		unbindSize().of(targetImageView);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetImageView); // still the old size
	}
	
	@Test
	public void unbindSizeOfControl() {
		bindSize().from(fromRectangle).to(targetControl);
		
		unbindSize().of(targetControl);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetControl); // still the old size
	}
	
	@Test
	public void unbindSizeOfRectangle() {
		bindSize().from(fromRectangle).to(targetRectangle);
		
		unbindSize().of(targetRectangle);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetRectangle); // still the old size
	}
	
	@Test
	public void unbindSizeOfRegion() {
		bindSize().from(fromRectangle).to(targetRegion);
		
		unbindSize().of(targetRegion);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetRegion); // still the old size
	}
}
