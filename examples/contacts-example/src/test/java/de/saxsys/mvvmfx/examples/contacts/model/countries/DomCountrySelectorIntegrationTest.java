package de.saxsys.mvvmfx.examples.contacts.model.countries;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JfxToolkitExtension.class)
public class DomCountrySelectorIntegrationTest implements CountrySelectorInterfaceTest {

	private CountrySelector countrySelector;

	@BeforeEach
	public void setup(){
		countrySelector = new DomCountrySelector();
	}


	@Override public CountrySelector getCountrySelector() {
		return countrySelector;
	}
}
