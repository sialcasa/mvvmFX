package de.saxsys.mvvmfx.examples.contacts.ui.addressform;

import de.saxsys.mvvmfx.examples.contacts.model.Country;
import de.saxsys.mvvmfx.examples.contacts.model.CountrySelector;
import de.saxsys.mvvmfx.examples.contacts.model.Subdivision;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import static de.saxsys.mvvmfx.examples.contacts.ui.addressform.AddressFormViewModel.NOTHING_SELECTED_MARKER;
import static de.saxsys.mvvmfx.examples.contacts.ui.addressform.AddressFormViewModel.SUBDIVISION_LABEL_KEY;
import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class AddressFormViewModelTest {
	private static final String SUBDIVISION_DEFAULT_LABEL = "default_subdivision_label";
	
	private AddressFormViewModel viewModel;
	
	private CountrySelector countrySelector;
	
	private Country germany = new Country("Germany", "DE");
	private Country austria = new Country("Austria", "AU");
	
	
	private StringProperty subdivisionLabel = new SimpleStringProperty();
	
	private ObservableList<Country> availableCountries = FXCollections.observableArrayList();
	private ObservableList<Subdivision> subdivisions = FXCollections.observableArrayList();

    private ContactDialogScope scope;
	
	@Before
	public void setup() {
		availableCountries.addAll(germany, austria);
		
		// sadly the ResourceBundle.getString method is final so we can't use mockito
		ResourceBundle resourceBundle = new ListResourceBundle() {
			@Override
			protected Object[][] getContents() {
				return new Object[][] {
						{ SUBDIVISION_LABEL_KEY, SUBDIVISION_DEFAULT_LABEL }
				};
			}
		};
		countrySelector = mock(CountrySelector.class);
		when(countrySelector.inProgressProperty()).thenReturn(new SimpleBooleanProperty());
		when(countrySelector.subdivisionLabel()).thenReturn(subdivisionLabel);
		when(countrySelector.availableCountries()).thenReturn(availableCountries);
		when(countrySelector.subdivisions()).thenReturn(subdivisions);
		
		// when "germany" is selected, fill in the subdivisions of germany ...
		doAnswer(invocation -> {
			helper_fillCountrySelectorWithGermanSubdivisions();
			return null;
		}).when(countrySelector).setCountry(germany);
		
		// ... same for austria
		doAnswer(invocation -> {
			helper_fillCountrySelectorWithAustrianSubdivisions();
			return null;
		}).when(countrySelector).setCountry(austria);
		
		// when nothing is selected, clear the subdivisions list.
		doAnswer(invocation -> {
			subdivisions.clear();
			return null;
		}).when(countrySelector).setCountry(null);



        scope = new ContactDialogScope();
		
		viewModel = new AddressFormViewModel();
        viewModel.dialogScope = scope;
		viewModel.resourceBundle = resourceBundle;
		viewModel.countrySelector = countrySelector;
	}
	
	@Test
	public void testSubdivisionLabel() {
		viewModel.initSubdivisionLabel();
		
		assertThat(viewModel.subdivisionLabel()).hasValue(SUBDIVISION_DEFAULT_LABEL);
		
		subdivisionLabel.set("Bundesland");
		assertThat(viewModel.subdivisionLabel()).hasValue("Bundesland");
		
		subdivisionLabel.set(null);
		assertThat(viewModel.subdivisionLabel()).hasValue(SUBDIVISION_DEFAULT_LABEL);
		
		subdivisionLabel.set("");
		assertThat(viewModel.subdivisionLabel()).hasValue(SUBDIVISION_DEFAULT_LABEL);
	}
	
	@Test
	public void testCountryAndFederalStateLists() throws Exception {
		viewModel.initialize();
		
		assertThat(viewModel.countriesList()).hasSize(3).contains(NOTHING_SELECTED_MARKER, "Austria", "Germany");
		assertThat(viewModel.countriesList().get(0)).isEqualTo(NOTHING_SELECTED_MARKER);
		assertThat(viewModel.subdivisionsList()).hasSize(1).contains(NOTHING_SELECTED_MARKER);
		
		assertThat(viewModel.selectedCountryProperty()).hasValue(NOTHING_SELECTED_MARKER);
		assertThat(viewModel.selectedSubdivisionProperty()).hasValue(NOTHING_SELECTED_MARKER);
		
		
		viewModel.selectedCountryProperty().set("Germany");
		
		
		
		assertThat(viewModel.subdivisionsList()).hasSize(17).contains(NOTHING_SELECTED_MARKER, "Sachsen", "Berlin",
				"Bayern"); // test sample
		assertThat(viewModel.subdivisionsList().get(0)).isEqualTo(NOTHING_SELECTED_MARKER);
		assertThat(viewModel.selectedSubdivisionProperty()).hasValue(NOTHING_SELECTED_MARKER);
		
		viewModel.selectedSubdivisionProperty().set("Sachsen");
		
		
		viewModel.selectedCountryProperty().set("Austria");
		
		assertThat(viewModel.selectedSubdivisionProperty()).hasValue(NOTHING_SELECTED_MARKER);
		assertThat(viewModel.subdivisionsList()).hasSize(10).contains(NOTHING_SELECTED_MARKER, "Wien", "Tirol",
				"Salzburg");
		assertThat(viewModel.subdivisionsList().get(0)).isEqualTo(NOTHING_SELECTED_MARKER);
		
		viewModel.selectedSubdivisionProperty().set("Wien");
		
		
		viewModel.selectedCountryProperty().set(NOTHING_SELECTED_MARKER);
		assertThat(viewModel.selectedSubdivisionProperty()).hasValue(NOTHING_SELECTED_MARKER);
		
		assertThat(viewModel.subdivisionsList()).hasSize(1).contains(NOTHING_SELECTED_MARKER);
	}
	
	@Test
	public void testCreateListWithNothingSelectedMarker() {
		ObservableList<String> sourceList = FXCollections.observableArrayList();
		
		ObservableList<String> target = AddressFormViewModel
				.createListWithNothingSelectedMarker(sourceList);
		
		assertThat(target).hasSize(1).contains(NOTHING_SELECTED_MARKER);
		
		sourceList.add("test");
		assertThat(target).hasSize(2).containsExactly(NOTHING_SELECTED_MARKER, "test");
		
		sourceList.add("temp");
		assertThat(target).hasSize(3).containsExactly(NOTHING_SELECTED_MARKER, "test", "temp");
		
		sourceList.remove("test");
		assertThat(target).hasSize(2).containsExactly(NOTHING_SELECTED_MARKER, "temp");
		
		
		sourceList.clear();
		assertThat(target).hasSize(1).contains(NOTHING_SELECTED_MARKER);
	}
	
	
	private void helper_fillCountrySelectorWithGermanSubdivisions() {
		subdivisions.clear();
		
		subdivisions.add(new Subdivision("Baden-Württemberg", "BW", germany));
		subdivisions.add(new Subdivision("Bayern", "BY", germany));
		subdivisions.add(new Subdivision("Berlin", "BE", germany));
		subdivisions.add(new Subdivision("Brandenburg", "BB", germany));
		subdivisions.add(new Subdivision("Bremen", "HB", germany));
		subdivisions.add(new Subdivision("Hamburg", "HH", germany));
		subdivisions.add(new Subdivision("Hessen", "HE", germany));
		subdivisions.add(new Subdivision("Mecklemburg-Vorpommern", "MV", germany));
		subdivisions.add(new Subdivision("Niedersachsen", "NI", germany));
		subdivisions.add(new Subdivision("Nordrhein-Westfalen", "NW", germany));
		subdivisions.add(new Subdivision("Rheinland-Pfalz", "RP", germany));
		subdivisions.add(new Subdivision("Saarland", "SL", germany));
		subdivisions.add(new Subdivision("Sachsen", "SN", germany));
		subdivisions.add(new Subdivision("Sachsen-Anhalt", "ST", germany));
		subdivisions.add(new Subdivision("Schleswig-Holstein", "SH", germany));
		subdivisions.add(new Subdivision("Thüringen", "TH", germany));
	}
	
	private void helper_fillCountrySelectorWithAustrianSubdivisions() {
		subdivisions.clear();
		
		subdivisions.add(new Subdivision("Burgenland", "Bgld.", austria));
		subdivisions.add(new Subdivision("Kärnten", "Ktn.", austria));
		subdivisions.add(new Subdivision("Niederösterreich", "NÖ", austria));
		subdivisions.add(new Subdivision("Oberösterreich", "OÖ", austria));
		subdivisions.add(new Subdivision("Salzburg", "Sbg.", austria));
		subdivisions.add(new Subdivision("Steiermark", "Stmk.", austria));
		subdivisions.add(new Subdivision("Tirol", "T", austria));
		subdivisions.add(new Subdivision("Vorarlberg", "Vbg.", austria));
		subdivisions.add(new Subdivision("Wien", "W", austria));
	}
	
}
