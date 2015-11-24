package de.saxsys.mvvmfx.guice.it;

import de.saxsys.mvvmfx.ViewModel;

import javax.inject.Inject;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class MyViewModel implements ViewModel {
	
	public static boolean works = false;
	
	@Inject
	public MyViewModel(MyService service) {
		Supplier<String> supplier = () -> "lambda in ViewModel works";
		assertThat(supplier.get()).isNotEmpty().contains("works");
		assertThat(service).isNotNull();
		works = true;
	}
}
