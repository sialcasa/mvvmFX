/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
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
package de.saxsys.mvvmfx.cdi;

import static org.assertj.core.api.Assertions.*;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.junit.Test;

import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;

public class MvvmfxCdiIntegrationTest {
	
	/**
	 * We use this class as our test application class.
	 */
	public static class MyApplication extends MvvmfxCdiApplication {
		
		public static boolean startMethodWasExecuted = false;
		public static boolean wasPostConstructCalled = false;
		public static boolean wasPreDestroyCalled = false;


		public static void main(String... args) {
			launch(args);
		}
		
		@Inject
		private InjectionExample injectionExample;
		
		@Override
		public void startMvvmfx(Stage stage) throws Exception {
			
			MyApplication.startMethodWasExecuted = true;
			
			
			assertThat(stage).isNotNull();
			assertThat(getParameters()).isNotNull();
			assertThat(getParameters().getUnnamed()).contains("test");
			
			assertThat(injectionExample).isNotNull();
			assertThat(injectionExample.notificationCenter).isNotNull();
			assertThat(injectionExample.hostServices).isNotNull();
			
			assertThat(MyApplication.wasPostConstructCalled).isTrue();
			
			// At this point preDestroy should not be called yet.
			assertThat(MyApplication.wasPreDestroyCalled).isFalse();
			
			
			// we can't shutdown the application in the test case so we need to do it here.
			Platform.exit();
		}


		@PostConstruct
		public void postConstruct(){
			wasPostConstructCalled = true;
		}

		@PreDestroy
		public void preDestroy(){
			wasPreDestroyCalled = true;
		}
	}
	
	
	public static class InjectionExample {

		@Inject
		NotificationCenter notificationCenter;

		@Inject
		HostServices hostServices;


		public static boolean wasPreDestroyCalled = false;

		@PreDestroy
		public void preDestroy(){
			wasPreDestroyCalled = true;
		}
	}
	
	/**
	 * Verify that after running the application there is a valid stage.
	 */
	@Test
	public void testApplicationWasStartedWithAStage() {
		MyApplication.wasPreDestroyCalled = false;
		MyApplication.wasPostConstructCalled = false;
		MyApplication.startMethodWasExecuted = false;
		
		InjectionExample.wasPreDestroyCalled = false;
		
		MyApplication.main("test");
		
		assertThat(MyApplication.startMethodWasExecuted).isTrue();
		
		assertThat(MyApplication.wasPreDestroyCalled).isTrue();
		
		assertThat(InjectionExample.wasPreDestroyCalled).isTrue();
	}
}
