package de.saxsys.mvvmfx.testingutils.jfxrunner;


import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * This basic class runner ensures that JavaFx is running and then wraps all the
 * runChild() calls in a Platform.runLater(). runChild() is called for each test
 * that is run. By wrapping each call in the Platform.runLater() this ensures
 * that the request is executed on the JavaFx thread.
 */
public class JfxRunner extends BlockJUnit4ClassRunner {
	/**
	 * Constructs a new JavaFxJUnit4ClassRunner with the given parameters.
	 *
	 * @param clazz
	 *            The class that is to be run with this Runner
	 * @throws InitializationError
	 *             Thrown by the BlockJUnit4ClassRunner in the super()
	 */
	public JfxRunner(final Class<?> clazz)
			throws InitializationError {
		super(clazz);
		
		new JFXPanel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runChild(final FrameworkMethod method, final RunNotifier notifier) {
		// Create a latch which is only removed after the super runChild()
		// method
		// has been implemented.
		final CountDownLatch latch = new CountDownLatch(1);

		// Check whether the method should run in FX-Thread or not.
		TestInJfxThread performMethodInFxThread = method.getAnnotation(TestInJfxThread.class);
		if (performMethodInFxThread != null) {
			Platform.runLater(() -> {
				JfxRunner.super.runChild(method, notifier);
				latch.countDown();
			});
		} else {
			JfxRunner.super.runChild(method, notifier);
			latch.countDown();
		}

		// Decrement the latch which will now proceed.

		try {
			latch.await();
		} catch (InterruptedException e) {
			// Waiting for the latch was interruped
			e.printStackTrace();
		}
	}
}