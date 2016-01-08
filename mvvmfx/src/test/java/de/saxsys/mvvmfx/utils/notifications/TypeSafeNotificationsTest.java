package de.saxsys.mvvmfx.utils.notifications;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class TypeSafeNotificationsTest {
	
	private TypeSafeNotificationCenter notificationCenter;
	
	
	public class ExampleNotification implements Notification {
	}
	public class OtherNotification implements Notification {
		private final String value;

		public OtherNotification(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	
	@Test
	public void api_notifications() {
		AtomicInteger countA = new AtomicInteger(0);
		AtomicInteger countB = new AtomicInteger(0);
		
		notificationCenter.subscribe(ExampleNotification.class, n -> countA.incrementAndGet());



		StringProperty lastTestValue = new SimpleStringProperty();
		
		notificationCenter.subscribe(OtherNotification.class, n -> {
			countB.incrementAndGet();
			lastTestValue.setValue(n.getValue());
		});
		
		// when
		notificationCenter.publish("something");
		
		assertThat(countA.get()).isEqualTo(0);
		assertThat(countB.get()).isEqualTo(0);
		
		
		notificationCenter.publish(new ExampleNotification());
		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(0);
		
		
		notificationCenter.publish(new OtherNotification("hello"));
		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(1);
		assertThat(lastTestValue.get()).isEqualTo("hello");
		
		notificationCenter.publish(new OtherNotification("test"));
		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(2);
		assertThat(lastTestValue.get()).isEqualTo("test");
		
	}
	
	@Test
	public void api_subscriptions() {
		AtomicInteger countA = new AtomicInteger(0);

		Consumer<String> observerA = message -> countA.incrementAndGet();
		Subscription<String> subscriptionA = notificationCenter.subscribe("a", observerA);
		
		assertThat(subscriptionA.getNotificationType()).isEqualTo(String.class);
		assertThat(subscriptionA.getObserver()).isEqualTo(observerA);
		
		
		notificationCenter.publish("a");
		assertThat(countA.get()).isEqualTo(1);
		
		
		subscriptionA.unsubscribe();
		
		notificationCenter.publish("a");
		assertThat(countA.get()).isEqualTo(1);




		AtomicInteger countB = new AtomicInteger(0);
		Consumer<ExampleNotification> observerB = notification -> countB.incrementAndGet();
		Subscription<ExampleNotification> subscriptionB = notificationCenter
				.subscribe(ExampleNotification.class, observerB);
		
		assertThat(subscriptionB.getObserver()).isEqualTo(observerB);
		assertThat(subscriptionB.getNotificationType()).isEqualTo(ExampleNotification.class);
		
		
		notificationCenter.publish(new ExampleNotification());
		assertThat(countB.get()).isEqualTo(1);
		
		subscriptionB.unsubscribe();
		
		notificationCenter.publish(new ExampleNotification());
		assertThat(countB.get()).isEqualTo(1);
	}
	
	
	@Test
	public void api_simpleNotifications() {
		AtomicInteger countA = new AtomicInteger(0);
		AtomicInteger countB = new AtomicInteger(0);
		
		notificationCenter.subscribe("a", message -> {
			countA.incrementAndGet();
			assertThat(message).isEqualTo("a");
		});
		notificationCenter.subscribe("b", message -> {
			countB.incrementAndGet();
			assertThat(message).isEqualTo("b");
		});
		
		
		assertThat(countA.get()).isEqualTo(0);
		assertThat(countB.get()).isEqualTo(0);
		
		
		// when
		notificationCenter.publish("a");
		// then
		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(0);



		// when
		notificationCenter.publish("b");
		// then
		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(1);
		
		
		
		// when
		notificationCenter.publish("c");

		// then
		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(1);
	}
	
	@Test
	public void api_channels() {
		AtomicInteger countA = new AtomicInteger(0);
		AtomicInteger countB = new AtomicInteger(0);
		AtomicInteger count3 = new AtomicInteger(0);
		AtomicInteger countBA = new AtomicInteger(0);

		TypeSafeNotificationCenter aCenter = notificationCenter.onChannel("a");

		TypeSafeNotificationCenter bCenter = notificationCenter.onChannel("b");

		TypeSafeNotificationCenter _3Center = notificationCenter.onChannel(3);


		TypeSafeNotificationCenter baCenter = bCenter.onChannel("a");
		
		
		
		aCenter.subscribe("test", m -> countA.incrementAndGet());
		bCenter.subscribe("test", m -> countB.incrementAndGet());
		_3Center.subscribe("test", m -> count3.incrementAndGet());
		baCenter.subscribe("test", m -> countBA.incrementAndGet());
		
		
		
		
		aCenter.publish("test");
		
		assertThat(countA.get()).isEqualTo(1); //
		assertThat(countB.get()).isEqualTo(0);
		assertThat(count3.get()).isEqualTo(0);
		assertThat(countBA.get()).isEqualTo(0);
		
		
		
		bCenter.publish("test");
		
		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(1); //
		assertThat(count3.get()).isEqualTo(0);
		assertThat(countBA.get()).isEqualTo(1); //



		baCenter.publish("test");

		assertThat(countA.get()).isEqualTo(1);
		assertThat(countB.get()).isEqualTo(1);
		assertThat(count3.get()).isEqualTo(0);
		assertThat(countBA.get()).isEqualTo(2); //

	}
}
