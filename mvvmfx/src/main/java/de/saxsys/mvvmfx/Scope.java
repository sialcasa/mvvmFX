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
package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;

/**
 * Scope.
 * 
 * @author alexander.casall
 *
 */
public interface Scope {

    default void publish(String messageName, Object... payload) {
        MvvmFX.getNotificationCenter().publish(this, messageName, payload);
    }

    default void subscribe(String messageName, NotificationObserver observer) {
        MvvmFX.getNotificationCenter().subscribe(this, messageName, observer);
    }

    default void unsubscribe(String messageName, NotificationObserver observer) {
        MvvmFX.getNotificationCenter().unsubscribe(this, messageName, observer);
    }

    default void unsubscribe(NotificationObserver observer) {
        MvvmFX.getNotificationCenter().unsubscribe(this, observer);
    }

}
