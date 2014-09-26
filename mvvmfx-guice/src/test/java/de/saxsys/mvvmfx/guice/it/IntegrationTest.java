package de.saxsys.mvvmfx.guice.it;

import static org.assertj.core.api.Assertions.*;

import javafx.application.Application;

import org.junit.Test;

public class IntegrationTest {
	
	@Test
	public void testInjectionOfPrimaryStage(){

		Application.launch(MyApp.class);
		
		assertThat(MyApp.viewTuple).isNotNull();
		assertThat(MyApp.stage).isNotNull();

		MyView codeBehind = MyApp.viewTuple.getCodeBehind();
		
		assertThat(codeBehind).isNotNull();
		assertThat(codeBehind.primaryStage).isNotNull();
		assertThat(codeBehind.primaryStage).isEqualTo(MyApp.stage);
	}
	
}
