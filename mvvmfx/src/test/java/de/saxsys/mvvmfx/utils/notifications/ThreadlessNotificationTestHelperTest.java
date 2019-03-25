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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.util.Pair;

public class ThreadlessNotificationTestHelperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none( );
    
    @Test
    public void receivedNotificationShouldStorePair() {
        final ThreadlessNotificationTestHelper testHelper = new ThreadlessNotificationTestHelper();
        testHelper.receivedNotification("someKey", "somePayload");
        
        assertThat(testHelper.numberOfReceivedNotifications()).isEqualTo(1);
        assertThat(testHelper.getReceivedNotifications()).hasSize(1);
        assertThat(testHelper.getReceivedNotifications().get(0).getKey()).isEqualTo("someKey");
        assertThat(testHelper.getReceivedNotifications().get(0).getValue()[0]).isEqualTo("somePayload");
    }
    
    @Test
    public void getReceivedNotificationsShouldReturnImmutableList() {
        final ThreadlessNotificationTestHelper testHelper = new ThreadlessNotificationTestHelper();
        final List<Pair<String, Object[]>> list = testHelper.getReceivedNotifications();
        
        expectedException.expect(UnsupportedOperationException.class);
        list.add(new Pair<String, Object[]>("someKey", new Object[] {"someValue"}));
    }
    
    @Test
    public void clearShouldRemoveAllNotifications() {
        final ThreadlessNotificationTestHelper testHelper = new ThreadlessNotificationTestHelper();
        testHelper.receivedNotification("someKey", "somePayload");
        
        testHelper.clear();
        assertThat(testHelper.numberOfReceivedNotifications()).isEqualTo(0);
        assertThat(testHelper.getReceivedNotifications()).isEmpty( );
    }
    
    @Test
    public void numberOfReceivedNotificationsShouldFilterCorrectly() {
        final ThreadlessNotificationTestHelper testHelper = new ThreadlessNotificationTestHelper();
        testHelper.receivedNotification("A", "somePayload");
        testHelper.receivedNotification("A", "somePayload");
        testHelper.receivedNotification("B", "somePayload");
        
        assertThat(testHelper.numberOfReceivedNotifications("A")).isEqualTo(2);
        assertThat(testHelper.numberOfReceivedNotifications("B")).isEqualTo(1);
        assertThat(testHelper.numberOfReceivedNotifications("C")).isEqualTo(0);
    }
    
    
    
}
