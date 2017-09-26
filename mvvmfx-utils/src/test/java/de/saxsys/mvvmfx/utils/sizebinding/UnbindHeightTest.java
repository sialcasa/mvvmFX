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
import static de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder.unbindHeight;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnbindHeightTest extends SizeBindingsBuilderTestBase {
	
	private ImageView targetImageView;
	private Control targetControl;
	private Rectangle targetRectangle;
	private Region targetRegion;
	
	
	@BeforeEach
	public void setUp() {
		targetImageView = new ImageView();
		targetControl = new ScrollPane();
		targetRectangle = new Rectangle();
		targetRegion = new Region();
	}
	
	@Test
	public void unbindHeightOfImageView() {
		bindHeight().from(fromRectangle).to(targetImageView);
		
		unbindHeight().of(targetImageView);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetImageView); // still the old size
	}
	
	@Test
	public void unbindHeightOfControl() {
		bindHeight().from(fromRectangle).to(targetControl);
		
		unbindHeight().of(targetControl);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetControl); // still the old size
	}
	
	@Test
	public void unbindHeightOfRectangle() {
		bindHeight().from(fromRectangle).to(targetRectangle);
		
		unbindHeight().of(targetRectangle);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetRectangle); // still the old size
	}
	
	@Test
	public void unbindHeightOfRegion() {
		bindHeight().from(fromRectangle).to(targetRegion);
		
		unbindHeight().of(targetRegion);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetRegion); // still the old size
	}
}
