/*******************************************************************************
 * Copyright 2013 Alexander Casall
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
package de.saxsys.jfx.mvvm.di.guice;

import java.io.IOException;
import java.net.URL;

import javafx.scene.Parent;

import javax.inject.Inject;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.cathive.fx.guice.GuiceFXMLLoader.Result;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * Guice specific implementation of the {@link FXMLLoaderWrapper}.
 * 
 * This class uses the {@link GuiceFXMLLoader} that is provided by the fx-guice
 * framework.
 * 
 * @author manuel.mauky
 * 
 */
public class GuiceFXMLLoaderWrapper implements FXMLLoaderWrapper {

	@Inject
	private GuiceFXMLLoader fxmlLoader;

	@Override
	public ViewTuple<? extends ViewModel> load(URL location) throws IOException {
		Result result = fxmlLoader.load(location);

		return new ViewTuple<>((View<?>) result.getController(), (Parent) result.getRoot());
	}

}
