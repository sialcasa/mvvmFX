/*******************************************************************************
 * Copyright 2018 Nils Christian Ehmke
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.saxsys.mvvmfx.utils.notifications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.util.Pair;

/**
 * The {@link ThreadlessNotificationTestHelper} is an alternative to the {@link NotificationTestHelper} and can be used
 * to test notifications, for instance from view models. Unlike the {@link NotificationTestHelper}, this class does not
 * use a thread and does also not rely on the JavaFX runtime environment being started. This makes this class useful in
 * unit tests within test environments where JavaFX can not be used.</br>
 * </br>
 * This class implements {@link NotificationObserver}, which means that it can be added as subscriber. It records every
 * received notification and can be queried afterwards.
 * 
 * @author Nils Christian Ehmke
 * 
 * @see NotificationTestHelper
 */
public class ThreadlessNotificationTestHelper implements NotificationObserver {

    private final List<Pair<String, Object[]>> notifications = new ArrayList<>( );

    @Override
    public void receivedNotification(final String key, final Object... payload) {
        notifications.add(new Pair<>(key, payload));
    }

    /**
     * Provides an unmodifiable list containing all received notifications.
     * 
     * @return All received notifications.
     */
    public List<Pair<String, Object[]>> getReceivedNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    /**
     * Provides the number of received notifications.
     * 
     * @return The number of received notifications.
     */
    public int numberOfReceivedNotifications() {
        return notifications.size();
    }

    /**
     * Provides the number of received notifications with a given key.
     * 
     * @param key
     *            The key of the notification.
     *            
     * @return The number of received notifications with the given key.
     */
    public int numberOfReceivedNotifications(String key) {
        return (int) notifications.parallelStream()
                                  .map(Pair::getKey)
                                  .filter(k -> k.equals(key))
                                  .count();
    }
    
    /**
     * Clears the list of received notifications.
     */
    public void clear() {
        notifications.clear();
    }

}
