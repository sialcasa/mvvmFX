package de.saxsys.mvvmfx.spring;

import de.saxsys.mvvmfx.ViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
public class MyViewModel implements ViewModel {
	public static int instanceCounter = 0;

	@Autowired
	private MyService myService;

	public MyViewModel() {
		instanceCounter++;
	}

}
