/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
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
package de.saxsys.jfx.mvvm.viewloader;

import static org.assertj.core.api.Assertions.*;

import javafx.scene.layout.VBox;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlView;

/**
 * This test verifies the behaviour of the {@link de.saxsys.jfx.mvvm.viewloader.ViewLoader} class.
 * <p/>
 * 
 * The purpose of this test case is to check the integration of the specific viewLoaders and some error handling.
 * 
 * @author manuel.mauky, alexander.casall
 */
public class ViewLoaderIntegrationTest {
	
	private ViewLoader viewLoader;
	
	@Before
	public void setup() {
		viewLoader = new ViewLoader();
	}
	
	
	@Test
	public void testLoadWithStringPath() {
		ViewTuple<? extends View, ? extends ViewModel> viewTuple = viewLoader
				.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/example/TestFxmlView.fxml");
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestFxmlView.class);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadFailNoValidContentInFxmlFile() {
		ViewTuple<? extends View, ? extends ViewModel> viewTuple = viewLoader
				.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/example/wrong.fxml");
	}
	
	
}
