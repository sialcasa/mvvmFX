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


import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.viewloader.example.InvalidFxmlTestView;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlView;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaView;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModel;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;


/**
 * This test verifies the behaviour of the {@link de.saxsys.jfx.mvvm.viewloader.ViewLoader} class.
 * <p/>
 * The actual loading of views is only tested on the surface as there are tests for the specific viewLoaders of
 * different Viewtypes (see {@link de.saxsys.jfx.mvvm.viewloader.JavaViewLoaderTest} and {@link
 * de.saxsys.jfx.mvvm.viewloader .FxmlViewLoaderTest}).
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


    /**
     * The purpose of this test case is to verify that the loading of JavaViews is working correctly. This contains the
     * resolving of the view type and casting.
     * <p/>
     * The actual loading of JavaViews with the {@link de.saxsys.jfx.mvvm.viewloader.JavaViewLoader} is tested in {@link
     * de.saxsys.jfx.mvvm.viewloader.JavaViewLoaderTest}.
     */
    @Test
    public void testLoadJavaView() {
        ViewTuple<TestViewModel> viewTuple = viewLoader.loadViewTuple(TestJavaView.class, null);

        assertThat(viewTuple).isNotNull();
    }


    /**
     * The purpose of this test case is to verify that the loading of FxmlViews is working correctly. This contains the
     * resolving of the view type and casting.
     * <p/>
     * The actual loading of JavaViews with the {@link de.saxsys.jfx.mvvm.viewloader.FxmlViewLoader} is tested in {@link
     * de.saxsys.jfx.mvvm.viewloader.FxmlViewLoaderTest}.
     */
    @Test
    public void testLoadFxmlView() {
        ViewTuple<TestViewModel> viewTuple = viewLoader.loadViewTuple(TestFxmlView.class, null);

        assertThat(viewTuple).isNotNull();
    }

    @Test
    public void testLoadWithStringPath() {
        ViewTuple<?> viewTuple = viewLoader.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/example/TestFxmlView.fxml");
        assertThat(viewTuple).isNotNull();

        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
        assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestFxmlView.class);
    }


    @Test
    public void testLoadFailNoSuchFxmlFile() {
        ViewTuple<TestViewModel> viewTuple = viewLoader.loadViewTuple(InvalidFxmlTestView.class);

        assertThat(viewTuple).isNull();
    }

    /**
     * In this test case a fxml file is loaded that has no fx:controller attribute. Therefore there is no code behind
     * available.
     */
    @Test
    public void testLoadFailNoControllerDefined() {
        ViewTuple<?> viewTuple = viewLoader.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/example" +
                "/TestFxmlViewWithoutController.fxml");
        assertThat(viewTuple).isNotNull();

        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
        assertThat(viewTuple.getCodeBehind()).isNull();
    }


    @Test
    public void testLoadFailNoValidContentInFxmlFile() {
        ViewTuple<?> viewTuple = viewLoader.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/example/wrong.fxml");
        assertThat(viewTuple).isNull();
    }

    /**
     * An exception may be thrown when you try to load a {@link de.saxsys.jfx.mvvm.base.view.View} without specifying a
     * ViewModel type.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadViewWithoutViewModelFail() {

        class MyView implements View<ViewModel> {
            @Override
            public void setViewModel(ViewModel viewModel) {

            }
        }

        viewLoader.loadViewTuple(MyView.class);
    }

}
