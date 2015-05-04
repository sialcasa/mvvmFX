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
 * Implementation of the Builder steps to unbind the size of a given component.
 *
 * @author manuel.mauky
 */
public class UnbindSizeBuilderStepImpl implements SizeBindingsBuilder.UnbindStep {
	@Override
	public void of(Region source) {
		new UnbindWidthBuilderStepImpl().of(source);
		new UnbindHeightBuilderStepImpl().of(source);
	}
	
	@Override
	public void of(Control source) {
		new UnbindWidthBuilderStepImpl().of(source);
		new UnbindHeightBuilderStepImpl().of(source);
	}
	
	@Override
	public void of(Rectangle source) {
		new UnbindWidthBuilderStepImpl().of(source);
		new UnbindHeightBuilderStepImpl().of(source);
	}
	
	@Override
	public void of(ImageView source) {
		new UnbindWidthBuilderStepImpl().of(source);
		new UnbindHeightBuilderStepImpl().of(source);
	}
}
