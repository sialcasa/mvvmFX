package de.saxsys.mvvmfx.guice.it;

import com.google.inject.AbstractModule;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class MyModule extends AbstractModule{
	
	public static boolean works = false;
	
	@Override
	protected void configure() {
		Supplier<String> supplier = ()-> "lambda in module works";
		
		assertThat(supplier.get()).isNotEmpty().contains("works");
		works = true;
		
		bind(MyService.class).to(MyServiceImpl.class);
	}
}
