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

import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import de.saxsys.mvvmfx.utils.sizebinding.SizeBindingsBuilder;

/**
 * Implementation of the Builder steps to bind the size of a given source component to the target component.
 *
 * @author manuel.mauky
 */
public class BindSizeBuilderStepImpl implements SizeBindingsBuilder.BindSizeBuilderStep,
		SizeBindingsBuilder.FromBindSizeBuilderStep {
	private SizeBindingsBuilder.FromBindWidthBuilderStep widthStep;
	private SizeBindingsBuilder.FromBindHeightBuilderStep heightStep;
	
	@Override
	public void to(Region target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public void to(Control target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public void to(Rectangle target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public void to(ImageView target) {
		widthStep.to(target);
		heightStep.to(target);
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(Region source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(Control source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(Rectangle source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
	
	@Override
	public SizeBindingsBuilder.FromBindSizeBuilderStep from(ImageView source) {
		widthStep = new BindWidthBuilderStepImpl().from(source);
		heightStep = new BindHeightBuilderStepImpl().from(source);
		return this;
	}
}
