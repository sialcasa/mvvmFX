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

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class TestViewA implements FxmlView<TestViewModelA> {
	
	@InjectViewModel
	public TestViewModelA viewModel;
	
	@FXML
	public VBox testViewB;
	
	@FXML
	public TestViewB testViewBController;
	
	public boolean initializeWasCalled = false;
	
	public void initialize() {
		initializeWasCalled = true;
	}
	
}
