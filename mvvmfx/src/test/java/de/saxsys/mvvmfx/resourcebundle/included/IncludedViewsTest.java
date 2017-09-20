/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
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
package de.saxsys.mvvmfx.resourcebundle.included;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test usage of resourcebundles for fx:include views. This is a test to reproduce the bug #235 (https://github.com/sialcasa/mvvmFX/issues/235)
 * 
 * @author manuel.mauky
 */
@ExtendWith(JfxToolkitExtension.class)
public class IncludedViewsTest {
	
	
	private ResourceBundle root;
	private ResourceBundle included;
	
	@BeforeEach
	public void setup(){
		MvvmFX.setGlobalResourceBundle(null);
		root = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".root");
		included = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".included");
	}

	@AfterEach
	public void tearDown() {
		MvvmFX.setGlobalResourceBundle(null);
	}


	@Test
	public void testWithRootBundle(){

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).resourceBundle(root)
				.load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;
		
		assertThat(includedView).isNotNull();
		
		
		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
	@Test
	public void testWithGlobalBundle() {
		MvvmFX.setGlobalResourceBundle(root);

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;

		assertThat(includedView).isNotNull();


		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
	@Test
	public void testWithGlobalAndRoot() {
		MvvmFX.setGlobalResourceBundle(root);

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class)
				.resourceBundle(root)
				.load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;

		assertThat(includedView).isNotNull();


		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
	@Test
	public void testWithoutRootBundle() {

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;

		assertThat(includedView).isNotNull();


		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
}
