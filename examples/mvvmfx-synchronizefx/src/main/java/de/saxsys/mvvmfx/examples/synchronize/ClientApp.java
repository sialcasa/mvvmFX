package de.saxsys.mvvmfx.examples.synchronize;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.synchronizefx.SynchronizeFxBuilder;
import de.saxsys.synchronizefx.core.clientserver.ClientCallback;
import de.saxsys.synchronizefx.core.clientserver.SynchronizeFxClient;
import de.saxsys.synchronizefx.core.exceptions.SynchronizeFXException;

/**
 * Client application's entry point.
 * 
 * @author manuel.mauky
 */
public class ClientApp extends Application{
	
	private static final String SERVER = "localhost";

	private SynchronizeFxClient client;
	
	public static void main(String...args){
		launch(args);
	}
	
	@Override 
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("MvvmFX and SynchronizeFX Example Client");
		
		client = SynchronizeFxBuilder.create().client().address(SERVER).callback(new ClientCallback() {
			@Override public void modelReady(Object object) {

				System.out.println("Ready");

				if (object instanceof SliderViewModel) {
					SliderViewModel viewModel = (SliderViewModel) object;

					ViewTuple<SliderView, SliderViewModel> viewTuple = FluentViewLoader.fxmlView(SliderView.class)
							.viewModel(viewModel).load();
					
					primaryStage.setScene(new Scene(viewTuple.getView(),400,200));
					
					primaryStage.show();
				} else {
					System.err.println("The type of the synchronized model is wrong!");
				}
			}

			@Override public void onError(SynchronizeFXException e) {
				System.out.println("Client Error: " + e.getLocalizedMessage());
			}

			@Override public void onServerDisconnect() {
				System.out.println("Server disconnected");
			}
		}).build();
		
		client.connect();
	}

	/**
	 * Disconnect the client when the application is closed.
	 */
	@Override 
	public void stop() throws Exception {
		System.out.print("Stopping the client...");
		client.disconnect();
		System.out.println("done");
	}
}
