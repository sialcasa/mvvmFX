package de.saxsys.mvvmfx.utils.notifications;

import de.saxsys.mvvmfx.testingutils.GCVerifier;
import org.junit.After;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * This test case shows how to use {@link WeakNotificationObserver}
 * to prevent memory leaks due to the notification center.
 */
public class MemoryLeakGlobalTest {


	@After
	public void tearDown() {
		NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());
	}

	/**
	 * This shows how a memory leak is introduced by using the notification center
	 * directly with a plain observer.
	 *
	 * @see #testMemoryLeak()
	 *
	 */
	public static class Subject1 {
		public AtomicInteger counter = new AtomicInteger();

		public void setup() {
			NotificationCenterFactory.getNotificationCenter()
					.subscribe("test", (k,p) -> counter.incrementAndGet());

		}
	}

	/**
	 * @see Subject1
	 */
	@Test
	public void testMemoryLeak() {
		Subject1 subject = new Subject1();

		subject.setup();

		GCVerifier.forceGC();

		assertThat(subject.counter.get()).isEqualTo(0);


		NotificationCenterFactory.getNotificationCenter()
				.publish("test");

		assertThat(subject.counter.get()).isEqualTo(1);

		GCVerifier verifier = GCVerifier.create(subject);


		subject = null;

		// subject creates a memory leak because a hard reference was used.
		assertThat(verifier.isAvailableForGC()).isFalse();
	}

	/**
	 * This shows how a memory leak is fixed by using
	 * a {@link WeakNotificationObserver} and a hard reference to the wrapped
	 * observer in a field of the subject class.
	 *
	 *
	 * @see #testMemoryLeakFixed()
	 *
	 */
	static class Subject2 {
		public AtomicInteger counter = new AtomicInteger();

		private NotificationObserver observer = (k,p) -> counter.incrementAndGet();

		public void setup() {
			NotificationCenterFactory.getNotificationCenter()
					.subscribe("test", new WeakNotificationObserver(observer));

		}
	}


	/**
	 * @see Subject2
	 */
	@Test
	public void testMemoryLeakFixed() {
		Subject2 subject = new Subject2();

		subject.setup();

		GCVerifier.forceGC();

		assertThat(subject.counter.get()).isEqualTo(0);


		NotificationCenterFactory.getNotificationCenter()
				.publish("test");

		assertThat(subject.counter.get()).isEqualTo(1);

		GCVerifier verifier = GCVerifier.create(subject);


		subject = null;

		// subject creates a memory leak because a hard reference was used.
		assertThat(verifier.isAvailableForGC()).isTrue();
	}

	/**
	 * This shows how to <strong>not</strong> fix a memory leak.
	 * In the subject a {@link WeakNotificationObserver} was used
	 * but no reference to the wrapped observer was keeped. Instead an inline lambda is used as observer.
	 * <p />
	 * The problem is that this way the wrapped observer will be prematurely garbage
	 * collected while the subject instance is still alive.
	 *
	 *
	 * @see #testInlineWeakObserverIsPrematurelyCollected()
	 */
	static class Subject3 {
		public AtomicInteger counter = new AtomicInteger();

		public void setup() {
			NotificationCenterFactory.getNotificationCenter()
					.subscribe("test", new WeakNotificationObserver((k, p) -> counter.incrementAndGet()));

		}
	}

	/**
	 * @see Subject3
	 */
	@Test
	public void testInlineWeakObserverIsPrematurelyCollected() {
		Subject3 subject = new Subject3();

		subject.setup();

		GCVerifier.forceGC();


		assertThat(subject.counter.get()).isEqualTo(0);


		NotificationCenterFactory.getNotificationCenter()
				.publish("test");

		// The observer is already garbage collected.
		// For this reason the counter wasn't increased.
		assertThat(subject.counter.get()).isEqualTo(0);
	}


}
