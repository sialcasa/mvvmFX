package de.saxsys.mvvmfx.examples.books;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication;
import de.saxsys.mvvmfx.examples.books.backend.LibraryService;
import de.saxsys.mvvmfx.examples.books.backend.LibraryServiceImpl;
import de.saxsys.mvvmfx.examples.books.backend.LibraryServiceMockImpl;
import eu.lestard.easydi.EasyDI;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends MvvmfxEasyDIApplication {
	
	private static final boolean ENABLE_MOCK_SERVICE = true;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	protected void initEasyDi(EasyDI context) throws Exception {
		if (ENABLE_MOCK_SERVICE) {
			context.bindInterface(LibraryService.class, LibraryServiceMockImpl.class);
			LibraryServiceMockImpl libraryServiceMock = context.getInstance(LibraryServiceMockImpl.class);
			libraryServiceMock.addSomeBooks();
		} else {
			context.bindInterface(LibraryService.class, LibraryServiceImpl.class);
		}
	}

	@Override
	public void startMvvmfx(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Library JavaFX");
		primaryStage.setMinWidth(1200);
		primaryStage.setMaxWidth(1200);
		primaryStage.setMinHeight(700);
		
		Scene scene = new Scene(FluentViewLoader.fxmlView(MainView.class).load().getView());
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
		FlatterFX.style(FlatterInputType.DEFAULT);
	}
}
