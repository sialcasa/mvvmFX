package de.saxsys.mvvmfx.guice;

import com.google.inject.Module;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test is used to reproduce a bug in the mvvmfx-guice module.
 * A class that is injected into the application is instantiated twice. 
 * 
 * see: <a href="https://github.com/sialcasa/mvvmFX/issues/124">issues 124</a>
 */
public class DuplicateInjectionBugTest {
	
	
	public static class A {
		public static int counter = 0;
		
		public A(){
			counter++;
		}
	}
	
	public static class B {
		public static int counter = 0;
		
		@Inject
		private A a;
		
		public B() {
				counter++;
		}
	}
	
	public static class MyApplication extends MvvmfxGuiceApplication {

		@Inject
		private B b;
		
		@Override 
		public void startMvvmfx(Stage stage) throws Exception {
			Platform.exit();
		}
	}
	
	
	@Test
	public void test(){
		B.counter = 0;
		A.counter = 0;
		Application.launch(MyApplication.class);
		
		assertThat(B.counter).isEqualTo(1);
		assertThat(A.counter).isEqualTo(1);
	}
	
}
