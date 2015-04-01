package de.saxsys.mvvmfx.examples.synchronize;

import de.saxsys.synchronizefx.SynchronizeFxBuilder;
import de.saxsys.synchronizefx.core.clientserver.ServerCallback;
import de.saxsys.synchronizefx.core.clientserver.SynchronizeFxServer;
import de.saxsys.synchronizefx.core.exceptions.SynchronizeFXException;

import java.util.Scanner;

public class ServerApp {
	
	public static void main(String... args) {
		System.out.println("Starting server");
		
		SliderViewModel centralViewModel = new SliderViewModel();
		
		final SynchronizeFxServer syncFxServer =
				SynchronizeFxBuilder.create().server().model(centralViewModel).callback(
						exception -> System.out.println("Server Error:" + exception.getLocalizedMessage())).build();
		
		syncFxServer.start();
		final Scanner console = new Scanner(System.in);
		boolean exit = false;
		while (!exit) {
			System.out.println("press 'q' for quit");
			final String input = console.next();
			if ("q".equals(input)) {
				exit = true;
			}
		}
		console.close();
		syncFxServer.shutdown();
		
		
	}
}
