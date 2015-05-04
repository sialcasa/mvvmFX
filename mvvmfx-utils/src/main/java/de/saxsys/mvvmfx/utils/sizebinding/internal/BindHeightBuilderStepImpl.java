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
package de.saxsys.mvvmfx.utils.sizebinding.internal;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder;

/**
 * Implementation of the Builder steps to bind the height of a given source component to the target component.
 * 
 * @author manuel.mauky
 */
public class BindHeightBuilderStepImpl implements SizeBindingsBuilder.BindHeightBuilderStep,
		SizeBindingsBuilder.FromBindHeightBuilderStep {
	
	private ReadOnlyDoubleProperty height;
	
	@Override
	public void to(Region target) {
		target.maxHeightProperty().bind(height);
		target.minHeightProperty().bind(height);
	}
	
	@Override
	public void to(Control target) {
		target.maxHeightProperty().bind(height);
		target.minHeightProperty().bind(height);
	}
	
	@Override
	public void to(Rectangle target) {
		target.heightProperty().bind(height);
	}
	
	@Override
	public void to(ImageView target) {
		target.fitHeightProperty().bind(height);
	}
	
	@Override
	public SizeBindingsBuilder.FromBindHeightBuilderStep from(Region source) {
		height = source.heightProperty();
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindHeightBuilderStep from(Control source) {
		height = source.heightProperty();
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindHeightBuilderStep from(Rectangle source) {
		height = source.heightProperty();
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindHeightBuilderStep from(ImageView source) {
		height = source.fitHeightProperty();
		return this;
	}
}
