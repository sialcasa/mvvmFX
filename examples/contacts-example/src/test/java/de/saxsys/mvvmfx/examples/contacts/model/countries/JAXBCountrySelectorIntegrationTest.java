package de.saxsys.mvvmfx.examples.contacts.model.countries;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JfxToolkitExtension.class)
public class JAXBCountrySelectorIntegrationTest implements CountrySelectorInterfaceTest {

    private CountrySelector countrySelector;

    @BeforeEach
    public void setup() {
        countrySelector = new JAXBCountrySelector();
    }

    @Override public CountrySelector getCountrySelector() {
        return countrySelector;
    }
}
