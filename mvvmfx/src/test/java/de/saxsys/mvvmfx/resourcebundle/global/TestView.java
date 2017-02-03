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
package de.saxsys.mvvmfx.resourcebundle.global;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

/**
 * @author manuel.mauky
 */
public class TestView implements FxmlView<TestViewModel> {

	@FXML
	public Label global_label;
	@FXML
	public Label other_label;
	@FXML
	public Label label;

	/**
	 * ResourceBundle injected via default JavaFX behavior. This would also work without mvvmFX
	 */
	public ResourceBundle resources;

	/**
	 * mvvmFX specific resourceBundle injection mechanism. This is redundant in the View class but it's the only
	 * way to get resourceBundles in the ViewModel and in Java-defined Views.
	 * Therefore some users may find it convenient to use the same mechanism in all places.
	 */
	@InjectResourceBundle
	public ResourceBundle mvvmFxResourceBundle;
	
}
