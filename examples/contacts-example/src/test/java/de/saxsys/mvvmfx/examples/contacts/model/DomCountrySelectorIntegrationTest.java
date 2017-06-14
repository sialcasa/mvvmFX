package de.saxsys.mvvmfx.examples.contacts.model;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
public class DomCountrySelectorIntegrationTest extends AbstractCountrySelectorTest {

	private CountrySelector countrySelector;

	@Before
	public void setup(){
		countrySelector = new DomCountrySelector();
	}


	@Override
	protected CountrySelector getCountrySelector() {
		return countrySelector;
	}
}
