package de.saxsys.mvvmfx.cdi.it;

import static org.assertj.core.api.Assertions.*;

import javafx.application.Application;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class IntegrationTest {
	
	@Before
	public void setup(){
		MyApp.wasPostConstructCalled = false;
		MyApp.wasPreDestroyCalled = false;
		MyApp.wasInitCalled = false;
		MyApp.wasStopCalled = false;
		
		MyViewModel.instanceCounter = 0;
		MyView.instanceCounter = 0;
		MyService.instanceCounter = 0;
	}
	
	@Test
	public void test(){
		assertThat(MyViewModel.instanceCounter).isEqualTo(0);
		assertThat(MyView.instanceCounter).isEqualTo(0);
		assertThat(MyService.instanceCounter).isEqualTo(0);


		Application.launch(MyApp.class, "testParam");

		assertThat(MyApp.wasPostConstructCalled).isTrue();
		assertThat(MyApp.wasPreDestroyCalled).isTrue();
		
		assertThat(MyApp.wasInitCalled).isTrue();
		assertThat(MyApp.wasStopCalled).isTrue();
		
		assertThat(MyApp.viewTuple).isNotNull();
		assertThat(MyApp.stage).isNotNull();

		MyView codeBehind = MyApp.viewTuple.getCodeBehind();
		
		assertThat(codeBehind).isNotNull();
		assertThat(codeBehind.primaryStage).isNotNull();
		assertThat(codeBehind.primaryStage).isEqualTo(MyApp.stage);
		
		assertThat(codeBehind.parameters).isNotNull();
		assertThat(codeBehind.parameters.getUnnamed()).contains("testParam");
		
		assertThat(codeBehind.hostServices).isNotNull();
		assertThat(codeBehind.notificationCenter).isNotNull();


		// reproduce bug #181 (<a href="https://github.com/sialcasa/mvvmFX/issues/181">issues 181</a>)
		assertThat(MyService.instanceCounter).isEqualTo(1);
		assertThat(MyView.instanceCounter).isEqualTo(1);
		assertThat(MyViewModel.instanceCounter).isEqualTo(1);
	}
}
