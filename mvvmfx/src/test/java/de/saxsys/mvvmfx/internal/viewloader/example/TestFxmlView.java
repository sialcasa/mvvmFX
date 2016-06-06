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
package de.saxsys.mvvmfx.internal.viewloader.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;


/**
 * This class is used as example View class that uses FXML.
 * 
 * @author manuel.mauky
 */
public class TestFxmlView implements FxmlView<TestViewModel>, Initializable {
	public URL url;
	public ResourceBundle resourceBundle;
	
	public static int instanceCounter = 0;
	
	@InjectViewModel
	private TestViewModel viewModel;
	
	public boolean viewModelWasNull = true;
	
	public TestFxmlView() {
		instanceCounter++;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.url = url;
		this.resourceBundle = resourceBundle;
		
		viewModelWasNull = viewModel == null;
	}
	
	public TestViewModel getViewModel() {
		return viewModel;
	}
	
}
