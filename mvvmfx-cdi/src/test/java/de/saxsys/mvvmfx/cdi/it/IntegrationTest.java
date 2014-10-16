package de.saxsys.mvvmfx.cdi.it;

import static org.assertj.core.api.Assertions.*;

import javafx.application.Application;

import org.junit.Before;
import org.junit.Test;

public class IntegrationTest {
	
	@Before
	public void setup(){
		MyApp.wasPostConstructCalled = false;
		MyApp.wasPreDestroyCalled = false;
		MyApp.wasInitCalled = false;
		MyApp.wasStopCalled = false;
	}
	
	@Test
	public void test(){
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
	}
	
}
