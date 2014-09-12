package de.saxsys.mvvmfx.contacts.ui.addressform;

import java.util.Optional;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.utils.itemlist.ListTransformation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Country;
import de.saxsys.mvvmfx.contacts.model.CountrySelector;
import de.saxsys.mvvmfx.contacts.model.Subdivision;
import de.saxsys.mvvmfx.utils.itemlist.ItemList;
import org.fxmisc.easybind.EasyBind;

public class AddressFormViewModel implements ViewModel {
	static final String NOTHING_SELECTED_MARKER = "---";
	static final String SUBDIVISION_LABEL_KEY = "addressform.subdivision.label";
	
	private ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper(true);
	private ObservableList<String> countries = FXCollections.observableArrayList();
	private ObservableList<String> subdivisions = FXCollections.observableArrayList();
	private ReadOnlyStringWrapper subdivisionLabel = new ReadOnlyStringWrapper();
	
	private StringProperty street = new SimpleStringProperty();
	private StringProperty postalCode = new SimpleStringProperty();
	private StringProperty city= new SimpleStringProperty();
	private StringProperty selectedCountry = new SimpleStringProperty(NOTHING_SELECTED_MARKER);
	private StringProperty selectedSubdivision = new SimpleStringProperty(NOTHING_SELECTED_MARKER);
	
	@Inject
	CountrySelector countrySelector;

	@Inject
	ResourceBundle resourceBundle;
	
	// Don't inline this field. It's needed to prevent the list mapping from being garbage collected.
	private ObservableList<String> subdivisionsMappingList;

	@PostConstruct
	public void init(){
		subdivisionLabel.bind(
				Bindings.when(
						countrySelector.subdivisionLabel().isEmpty())
						.then(resourceBundle.getString(SUBDIVISION_LABEL_KEY))
						.otherwise(countrySelector.subdivisionLabel()));

		countries = createListWithNothingSelectedMarker(
				EasyBind.map(countrySelector.availableCountries(), Country::getName));

		subdivisionsMappingList = EasyBind.map(countrySelector.subdivisions(), Subdivision::getName);
		subdivisions = createListWithNothingSelectedMarker(subdivisionsMappingList);
		
		selectedCountry.addListener((obs, oldV, newV) -> {
			if (newV != null && !newV.equals(NOTHING_SELECTED_MARKER)) {
				Optional<Country> matchingCountry = countrySelector.availableCountries().stream()
						.filter(country -> newV.equals(country.getName()))
						.findFirst();
				
				if (matchingCountry.isPresent()) {
					countrySelector.setCountry(matchingCountry.get());
				}
			} else if(NOTHING_SELECTED_MARKER.equals(newV)){
				countrySelector.setCountry(null);
			}
			selectedSubdivision.set(NOTHING_SELECTED_MARKER);
		});
	}


	/**
	 * Creates an observable list that always has {@link #NOTHING_SELECTED_MARKER} as first element
	 * and the values of the given observable list.
	 */
	static ObservableList<String> createListWithNothingSelectedMarker(ObservableList<String> source){
		final ObservableList<String> result = FXCollections.observableArrayList();
		result.add(NOTHING_SELECTED_MARKER);
		result.addAll(source);
		source.addListener((ListChangeListener<String>) c -> {
			result.clear();
			result.add(NOTHING_SELECTED_MARKER);
			result.addAll(source);
		});
		return result;
	}

	public ReadOnlyBooleanProperty validProperty() {
		return valid.getReadOnlyProperty();
	}
	
	
	public ObservableList<String> countriesList(){
		return countries;
	}
	
	public ObservableList<String> subdivisionsList(){
		return subdivisions;
	}
	
	public StringProperty streetProperty(){
		return street;
	}
	
	public StringProperty cityProperty(){
		return city;
	}
	
	public StringProperty postalCodeProperty(){
		return postalCode;
	}
	
	public StringProperty selectedCountryProperty(){
		return selectedCountry;
	}
	
	public StringProperty selectedSubdivisionProperty(){
		return selectedSubdivision;
	}
	
	public ReadOnlyStringProperty subdivisionLabel(){
		return subdivisionLabel.getReadOnlyProperty();
	}
}
