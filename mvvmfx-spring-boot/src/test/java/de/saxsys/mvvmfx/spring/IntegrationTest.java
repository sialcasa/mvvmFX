package de.saxsys.mvvmfx.spring;

import javafx.application.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

	@BeforeEach
	public void setup() {
		MyApp.wasPostConstructCalled = false;
		MyApp.wasPreDestroyCalled = false;
		MyApp.wasInitCalled = false;
		MyApp.wasStopCalled = false;

		MyViewModel.instanceCounter = 0;
		MyView.instanceCounter = 0;
		MyService.instanceCounter = 0;
	}

	@Test
	public void test() {
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

		assertThat(codeBehind.hostServices).isNotNull();
		assertThat(codeBehind.notificationCenter).isNotNull();

		assertThat(MyService.instanceCounter).isEqualTo(1);
		assertThat(MyView.instanceCounter).isEqualTo(1);
		assertThat(MyViewModel.instanceCounter).isEqualTo(1);
	}
}
