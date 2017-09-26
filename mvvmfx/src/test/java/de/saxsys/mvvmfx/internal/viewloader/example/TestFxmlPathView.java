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

import de.saxsys.mvvmfx.FxmlPath;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class is used as example View class that uses a custom path FXML.
 *
 * @authors manuel.mauky, rafael.guillen
 */
@FxmlPath(value = "/de/saxsys/mvvmfx/internal/viewloader/example/TestFxmlViewWithCustomPath.fxml")
public class TestFxmlPathView implements FxmlView<TestViewModel>, Initializable {
    public static int instanceCounter = 0;
    public URL url;
    public ResourceBundle resourceBundle;
    public boolean viewModelWasNull = true;
    @InjectViewModel
    private TestViewModel viewModel;

    public TestFxmlPathView() {
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
