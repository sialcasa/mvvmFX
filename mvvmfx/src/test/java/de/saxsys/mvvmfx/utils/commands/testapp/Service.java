package de.saxsys.mvvmfx.utils.commands.testapp;

import java.util.Random;

/**
 * @author manuel.mauky
 */
public class Service {
	
	
	
	public int longRunningService() {
		try {
			int waitTime = (new Random().nextInt(3) + 1) * 1000; // between 1 and 3 seconds

			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new Random().nextInt(6)+1;
	}
	
}
