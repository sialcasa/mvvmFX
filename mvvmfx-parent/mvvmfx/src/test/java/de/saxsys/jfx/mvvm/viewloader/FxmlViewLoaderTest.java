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
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlView;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewWithoutViewModel;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModel;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the loading of FxmlViews.
 * 
 * @author manuel.mauky 
 */
public class FxmlViewLoaderTest {
    
    private FxmlViewLoader fxmlViewLoader;
    
    @Before
    public void setup(){
        fxmlViewLoader = new FxmlViewLoader();
    }
    
    @Test
    public void testLoadFxmlViewTuple() throws IOException{
        ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));

        ViewTuple<TestFxmlView, TestViewModel> viewTuple = fxmlViewLoader.loadFxmlViewTuple(TestFxmlView.class, resourceBundle);

        assertThat(viewTuple).isNotNull();

        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
        assertThat(viewTuple.getCodeBehind()).isNotNull();

        TestFxmlView codeBehind = viewTuple.getCodeBehind();
        assertThat(codeBehind.viewModel).isNotNull();
        assertThat(codeBehind.resourceBundle).isEqualTo(resourceBundle);
        
        assertThat(codeBehind.viewModelWasNull).isFalse();
    }
    
    @Test
    public void testLoadFxmlViewTupleWithoutViewModel(){

        ViewTuple viewTuple = fxmlViewLoader.loadFxmlViewTuple
                (TestFxmlViewWithoutViewModel.class, null);
        
        assertThat(viewTuple).isNotNull();
    
        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
        assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestFxmlViewWithoutViewModel.class);

        TestFxmlViewWithoutViewModel codeBehind = (TestFxmlViewWithoutViewModel)viewTuple.getCodeBehind();
        
        assertThat(codeBehind.wasInitialized).isTrue();
        assertThat(codeBehind.viewModel).isNull();
    }
    
}
