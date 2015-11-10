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

import de.saxsys.mvvmfx.ViewModel;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This test verifies the behaviour of the publish/subscribe mechanism of ViewModels when no JavaFX thread is running.
 * In this case the publish/subscribe should still be working.
 * 
 * @author manuel.mauky
 */
public class ViewModelWithoutUiThreadTest {
	
	public static class MyViewModel implements ViewModel {
	}
	
	
	@Test
	public void test() throws InterruptedException, ExecutionException, TimeoutException {
		
		MyViewModel viewModel = new MyViewModel();
		
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		viewModel.subscribe("test", (key, payload) -> {
			future.complete(null);
		});
		
		
		viewModel.publish("test");
		
		future.get(1l, TimeUnit.SECONDS);
	}
}
