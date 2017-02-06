package de.saxsys.mvvmfx.examples.async_todoapp_futures;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItemService;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItemServiceImpl;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.ui.MainView;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.ui.TodoScope;
import eu.lestard.easydi.EasyDI;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends MvvmfxEasyDIApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initEasyDi(EasyDI context) throws Exception {
        context.bindInterface(TodoItemService.class, TodoItemServiceImpl.class);
    }

    @Override
    public void startMvvmfx(Stage stage) throws Exception {
        stage.setTitle("TodoApp with CompletableFutures");

        TodoScope todoScope = new TodoScope();

        final Parent view = FluentViewLoader
                .fxmlView(MainView.class)
                .providedScopes(todoScope)
                .load()
                .getView();
        stage.setScene(new Scene(view));
        stage.show();
    }
}
