package de.saxsys.mvvmfx.examples.helloworld.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {
	@Override
	public String greet(String name) {
		return "Hello " + name + ". How are you?";
	}
}
