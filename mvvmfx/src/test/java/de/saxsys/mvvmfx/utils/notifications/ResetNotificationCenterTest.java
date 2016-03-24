/*******************************************************************************
 * Copyright 2016 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.notifications;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.fail;

/**
 * This test case is used to see how you can reset the notification center
 * for unit tests.
 *
 * In this example we have two test cases that both are publishing the same message key.
 * However in the setup method we are defining a subscriber for this message key that will fail when it is called more then once.
 *
 * To get this tests green we need to reset the notification center for each test so that each test has a fresh notification center instance.
 * This is done in the tearDown method.
 *
 */
public class ResetNotificationCenterTest {


    private static final String MY_MESSAGE = "myMessage";
    private AtomicBoolean wasCalled = new AtomicBoolean(false);

    @Before
    public void setup() {
        NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

        notificationCenter.subscribe(MY_MESSAGE, (key, payload) -> {
            if(wasCalled.get()) {
                fail("subsciber is called more then once");
            } else {
                wasCalled.set(true);
            }
        });
    }


    /**
     * This is the important part. If we wouldn't replace the notification center instance,
     * one of the test cases would fail.
     * By replacing the instance we assure that each test uses a fresh notification center.
     */
    @After
    public void tearDown() {

        NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());

    }


    @Test
    public void testOne() {

        NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

        notificationCenter.publish(MY_MESSAGE);

    }

    @Test
    public void testTwo() {

        NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();

        notificationCenter.publish(MY_MESSAGE);
    }
}
