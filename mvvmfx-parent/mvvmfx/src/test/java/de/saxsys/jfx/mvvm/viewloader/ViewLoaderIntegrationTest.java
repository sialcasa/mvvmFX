package de.saxsys.jfx.mvvm.viewloader;


import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewLoaderIntegrationTest {

    private ViewLoader viewLoader;

    @Before
    public void setup(){
        viewLoader = new ViewLoader();
    }


    @Test
    public void testLoadViewTuple(){
        ViewTuple<TestViewModel> viewTuple = viewLoader.loadViewTuple(TestView.class);

        assertThat(viewTuple).isNotNull();

        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
        assertThat(viewTuple.getCodeBehind()).isNotNull();
        TestView codeBehind = (TestView)viewTuple.getCodeBehind();
        assertThat(codeBehind.resourceBundle).isNull();
    }

    @Test
    public void testLoadWithStringPath(){
        ViewTuple<?> viewTuple = viewLoader.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/testview.fxml");
        assertThat(viewTuple).isNotNull();

        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
        assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestView.class);
    }

    @Test
    public void testLoadWithResourceBundle() throws IOException {
        ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));

        ViewTuple<TestViewModel> viewTuple = viewLoader.loadViewTuple(TestView.class,resourceBundle);

        assertThat(viewTuple).isNotNull();

        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);


        assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestView.class);
        TestView codeBehind = (TestView)viewTuple.getCodeBehind();
        assertThat(codeBehind.resourceBundle).isEqualTo(resourceBundle);
    }

    @Test
    public void testLoadFailNoSuchFxmlFile(){
        ViewTuple<TestViewModel> viewTuple = viewLoader.loadViewTuple(InvalidTestView.class);

        assertThat(viewTuple).isNull();
    }

    /**
     * In this test case a fxml file is loaded that has no fx:controller attribute.
     * Therefore there is no code behind available.
     */
    @Test
    public void testLoadFailNoControllerDefined(){
        ViewTuple<?> viewTuple = viewLoader.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/testviewWithoutController.fxml");
        assertThat(viewTuple).isNotNull();

        assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
        assertThat(viewTuple.getCodeBehind()).isNull();
    }


    @Test
    public void testLoadFailNoValidContentInFxmlFile(){
        ViewTuple<?> viewTuple = viewLoader.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/wrong.fxml");
        assertThat(viewTuple).isNull();
    }

}
