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
 * Implementation of the Builder steps to bind the width of a given source component to the target component.
 *
 * @author manuel.mauky
 */
public class BindWidthBuilderStepImpl implements SizeBindingsBuilder.BindWidthBuilderStep,
		SizeBindingsBuilder.FromBindWidthBuilderStep {
	
	private ReadOnlyDoubleProperty width;
	
	@Override
	public void to(Region target) {
		target.maxWidthProperty().bind(width);
		target.minWidthProperty().bind(width);
	}
	
	@Override
	public void to(Control target) {
		target.maxWidthProperty().bind(width);
		target.minWidthProperty().bind(width);
	}
	
	@Override
	public void to(Rectangle target) {
		target.widthProperty().bind(width);
	}
	
	@Override
	public void to(ImageView target) {
		target.fitWidthProperty().bind(width);
	}
	
	@Override
	public SizeBindingsBuilder.FromBindWidthBuilderStep from(Region source) {
		width = source.widthProperty();
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindWidthBuilderStep from(Control source) {
		width = source.widthProperty();
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindWidthBuilderStep from(Rectangle source) {
		width = source.widthProperty();
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindWidthBuilderStep from(ImageView source) {
		width = source.fitWidthProperty();
		return this;
	}
}
