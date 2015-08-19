package de.saxsys.mvvmfx.utils.notifications;

import de.saxsys.mvvmfx.testingutils.GCVerifier;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * This test case is used to reproduce the <a href="https://github.com/sialcasa/mvvmFX/issues/286">bug #286</a>.
 *
 * A class that subscribes to the {@link NotificationCenter} can't be garbage collected.
 */
public class GarbageCollectionTest {


    public static int globalCounter = 0;

    // it can make a difference if a callback is created as lambda or anonymous inner class
    public class ExampleWithAnonymousInnerClassObserver {

        public int localCounter = 0;

        private NotificationObserver notificationObserver;

        public void subscribe() {
            final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

            notificationObserver = new NotificationObserver() {
                @Override
                public void receivedNotification(String key, Object... payload) {
                    globalCounter++;
                    localCounter++;
                }
            };
            notificationCenter.subscribe("test", notificationObserver);
        }

        public void unsubscribe() {
            final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

            notificationCenter.unsubscribe(notificationObserver);
        }
    }

    public class ExampleWithLambdaObserver {

        public int localCounter = 0;

        private NotificationObserver notificationObserver;

        public void subscribe() {
            final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

            notificationObserver = (key, payload) -> {
                globalCounter++;
                localCounter++;
            };
            notificationCenter.subscribe("test", notificationObserver);
        }

        public void unsubscribe() {
            final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

            notificationCenter.unsubscribe(notificationObserver);
        }
    }


    @Before
    public void setup() {
        NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());
    }


    @Test
    public void testWithExplicitUnsubscribeAndAnonymousInnerClass() {
        globalCounter = 0;

        ExampleWithAnonymousInnerClassObserver example = new ExampleWithAnonymousInnerClassObserver();
        example.subscribe();

        final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

        assertThat(globalCounter).isEqualTo(0);
        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);

        example.unsubscribe();


        final GCVerifier verifier = GCVerifier.create(example);

        example = null;

        verifier.verify();

        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);
    }


    @Test
    public void testWithoutExplicitUnsubscribeAndAnonymousInnerclass() {
        globalCounter = 0;

        ExampleWithAnonymousInnerClassObserver example = new ExampleWithAnonymousInnerClassObserver();
        example.subscribe();

        final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

        assertThat(globalCounter).isEqualTo(0);
        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);


        final GCVerifier verifier = GCVerifier.create(example);

        example = null;

        verifier.verify();

        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);

    }



    @Test
    public void testWithExplicitUnsubscribeAndLambda() {
        globalCounter = 0;

        ExampleWithLambdaObserver example = new ExampleWithLambdaObserver();
        example.subscribe();

        final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

        assertThat(globalCounter).isEqualTo(0);
        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);

        example.unsubscribe();


        final GCVerifier verifier = GCVerifier.create(example);

        example = null;

        verifier.verify();

        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);
    }


    @Test
    public void testWithoutExplicitUnsubscribeAndLambda() {
        globalCounter = 0;

        ExampleWithLambdaObserver example = new ExampleWithLambdaObserver();
        example.subscribe();

        final NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

        assertThat(globalCounter).isEqualTo(0);
        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);


        final GCVerifier verifier = GCVerifier.create(example);

        example = null;

        verifier.verify();

        notificationCenter.publish("test");
        assertThat(globalCounter).isEqualTo(1);

    }

}
