package de.saxsys.mvvmfx.spring;

import org.springframework.stereotype.Service;

@Service
public class MyService {
	public static int instanceCounter = 0;

	public MyService() {
		instanceCounter++;
	}

}
