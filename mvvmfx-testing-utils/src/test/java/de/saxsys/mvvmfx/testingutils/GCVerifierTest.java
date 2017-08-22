package de.saxsys.mvvmfx.testingutils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class GCVerifierTest {
	
	@Test
	public void testCollectable() {
		
		Object testObject = new Object();
		
		GCVerifier verifier = GCVerifier.create(testObject);
		
		assertThat(testObject).isNotNull();
		
		testObject = null;
		
		
		verifier.verify();
	}
	
	@Test
	public void testNotCollectable() {
		Object testObject = new Object();
		
		GCVerifier verifier = GCVerifier.create(testObject);
		
		try {
			verifier.verify();
			fail("Expected an AssertionError");
		} catch (AssertionError error) {
			assertThat(error).hasMessageContaining("Garbage Collection");
		}
	}
	
}
