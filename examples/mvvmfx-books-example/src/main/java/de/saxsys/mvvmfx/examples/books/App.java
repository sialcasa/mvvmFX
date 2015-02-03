package de.saxsys.mvvmfx.examples.books;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.examples.books.backend.Book;
import de.saxsys.mvvmfx.examples.books.backend.LibraryService;
import de.saxsys.mvvmfx.examples.books.backend.LibraryServiceImpl;
import de.saxsys.mvvmfx.examples.books.backend.LibraryServiceMockImpl;
import eu.lestard.easydi.EasyDI;

public class App extends Application {
	
	private static final boolean ENABLE_MOCK_SERVICE = false;
	
	private EasyDI context = new EasyDI();
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		if (ENABLE_MOCK_SERVICE) {
			context.bindInterface(LibraryService.class, LibraryServiceMockImpl.class);
			initMockService();
		} else {
			context.bindInterface(LibraryService.class, LibraryServiceImpl.class);
		}
		
		
		MvvmFX.setCustomDependencyInjector(context::getInstance);
		
		primaryStage.setTitle("Library JavaFX");
		primaryStage.setMinWidth(1200);
		primaryStage.setMaxWidth(1200);
		primaryStage.setMinHeight(700);
		
		Scene scene = new Scene(FluentViewLoader.fxmlView(MainView.class).load().getView(), 1200, 700);
		
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
		FlatterFX.style(FlatterInputType.DEFAULT);
	}
	
	private void initMockService() {
		LibraryServiceMockImpl libraryServiceMock = context.getInstance(LibraryServiceMockImpl.class);
		
		libraryServiceMock.addBooks(
				new Book("/1", "A Game of Thrones", "Georg R. R. Martin", "First part of SOIF"),
				new Book("/2", "A Clash of Kings", "Georg R. R. Martin", "Second part of SOIF"),
				new Book("/3", "A Storm of Swords", "Georg R. R. Martin", "Third part of SOIF"),
				new Book("/4", "The Metamorphosis", "Franz Kafka", "A man turns into an insect")
				);
	}
}
