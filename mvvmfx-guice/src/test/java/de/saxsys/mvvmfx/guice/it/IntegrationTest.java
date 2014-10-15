package de.saxsys.mvvmfx.guice.it;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.saxsys.mvvmfx.guice.internal.GuiceInjector;

public class IntegrationTest {
	
	@Test
	public void testInjectionOfPrimaryStage(){
		MyApp.main("test");


		assertThat(MyApp.stage).isNotNull();
		assertThat(MyApp.parameters).isNotNull();
		assertThat(MyApp.parameters.getUnnamed()).contains("test");
		
		GuiceInjector injector = MyApp.staticInjector;
		assertThat(injector).isNotNull();


		assertThat(MyApp.viewTuple).isNotNull();

		MyView codeBehind = MyApp.viewTuple.getCodeBehind();
		
		assertThat(codeBehind).isNotNull();
		assertThat(codeBehind.primaryStage).isNotNull();
		assertThat(codeBehind.primaryStage).isEqualTo(MyApp.stage);
		
		assertThat(codeBehind.notificationCenter).isNotNull();
		assertThat(codeBehind.hostServices).isNotNull();
		
		assertThat(codeBehind.parameters).isNotNull();
		assertThat(codeBehind.parameters.getUnnamed()).contains("test");

	}
	
}
