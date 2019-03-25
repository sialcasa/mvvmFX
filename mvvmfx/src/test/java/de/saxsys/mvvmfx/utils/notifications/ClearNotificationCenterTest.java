/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test-case of <a href="https://github.com/sialcasa/mvvmFX/issues/585">Issue #585</a>.
 */
class ClearNotificationCenterTest {
    private static final String TEST_NOTIFICATION = "test_notification";

    private NotificationCenter defaultCenter;

    NotificationObserver notificationObserver;

    @BeforeEach
    public void init() {
        notificationObserver = Mockito.mock(NotificationObserver.class);
        defaultCenter = new DefaultNotificationCenter();
    }

    @Test
    void testClearObservers() {
        defaultCenter.subscribe(TEST_NOTIFICATION, notificationObserver);

        defaultCenter.clear();

        Mockito.verifyNoMoreInteractions(notificationObserver);

        defaultCenter.publish(TEST_NOTIFICATION);
    }
}