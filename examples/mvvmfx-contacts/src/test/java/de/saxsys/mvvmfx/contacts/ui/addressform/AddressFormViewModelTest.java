package de.saxsys.mvvmfx.contacts.ui.addressform;

import de.saxsys.mvvmfx.contacts.model.Country;
import de.saxsys.mvvmfx.contacts.model.CountryFactory;
import de.saxsys.mvvmfx.contacts.model.Repository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static eu.lestard.assertj.javafx.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressFormViewModelTest {
	
	private static final String NOTHING_SELECTED = "---"; // the string that is used to mark "nothing selected".
	
	private AddressFormViewModel viewModel;
	
	private Repository repository;
	
	@Before
	public void setup(){
		repository = mock(Repository.class);
		Set<Country> allCountries = new HashSet<>();
		allCountries.add(CountryFactory.createGermany());
		allCountries.add(CountryFactory.createAustria());

		when(repository.findAllCountries()).thenReturn(allCountries);
		
		viewModel = new AddressFormViewModel();
		viewModel.repository = repository;
	}
	
	@Test
	public void testCountryAndFederalStateLists(){
		
		viewModel.init();
		
		assertThat(viewModel.countriesList()).hasSize(3).contains(NOTHING_SELECTED,"Österreich", "Deutschland");
		assertThat(viewModel.countriesList().get(0)).isEqualTo(NOTHING_SELECTED);
		assertThat(viewModel.federalStatesList()).hasSize(1).contains(NOTHING_SELECTED);
		
		assertThat(viewModel.selectedCountryProperty()).hasValue(NOTHING_SELECTED);
		assertThat(viewModel.selectedFederalStateProperty()).hasValue(NOTHING_SELECTED);
		
		
		
		viewModel.selectedCountryProperty().set("Deutschland");
		
		assertThat(viewModel.federalStatesList()).hasSize(17).contains(NOTHING_SELECTED, "Sachsen", "Berlin", "Bayern"); // test sample
		assertThat(viewModel.federalStatesList().get(0)).isEqualTo(NOTHING_SELECTED);
		assertThat(viewModel.selectedFederalStateProperty()).hasValue(NOTHING_SELECTED);
		
		viewModel.selectedFederalStateProperty().set("Sachsen");
		
		
		viewModel.selectedCountryProperty().set("Österreich");
		
		assertThat(viewModel.selectedFederalStateProperty()).hasValue(NOTHING_SELECTED);
		assertThat(viewModel.federalStatesList()).hasSize(10).contains(NOTHING_SELECTED,"Wien", "Tirol", "Salzburg");
		assertThat(viewModel.federalStatesList().get(0)).isEqualTo(NOTHING_SELECTED);
		
		viewModel.selectedFederalStateProperty().set("Wien");
		
		
		viewModel.selectedCountryProperty().set(NOTHING_SELECTED);
		assertThat(viewModel.selectedFederalStateProperty()).hasValue(NOTHING_SELECTED);
	
		assertThat(viewModel.federalStatesList()).hasSize(1).contains(NOTHING_SELECTED);
	}
}
