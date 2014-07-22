package de.saxsys.jfx.mvvm.cdi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Test;

import java.sql.SQLOutput;

import static org.assertj.core.api.Assertions.assertThat;

public class MvvmfxCdiIntegrationTest {

    /**
     * We use this class as our test application class.
     */
    public static class MyApplication extends MvvmfxCdiApplication{

        /**
         * To be able to verify that there was a valid stage available we need to persist the stage so we can verify it after the application has stopped.
         * This needs to be static because we can't create an instance of this Application class on our own. This is done by the framework.
         */
        public static Stage stage;

        public static Application.Parameters parameters;

        public static void main(String...args){
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {
            MyApplication.stage = stage;

            MyApplication.parameters = getParameters();

            // we can't shutdown the application in the test case so we need to do it here.
            Platform.exit();
        }
    }


    /**
     * Verify that after running the application there is a valid stage.
     */
    @Test
    public void testApplicationWasStartedWithAStage(){
        MyApplication.main("test");

        assertThat(MyApplication.stage).isNotNull();
        assertThat(MyApplication.parameters).isNotNull();
        assertThat(MyApplication.parameters.getUnnamed()).contains("test");
    }



}
