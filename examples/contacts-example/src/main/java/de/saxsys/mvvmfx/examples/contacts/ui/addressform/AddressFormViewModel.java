package de.saxsys.mvvmfx.examples.contacts.ui.addressform;

import java.util.Optional;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Address;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Country;
import de.saxsys.mvvmfx.examples.contacts.model.CountrySelector;
import de.saxsys.mvvmfx.examples.contacts.model.Subdivision;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import de.saxsys.mvvmfx.utils.itemlist.ItemList;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class AddressFormViewModel implements ViewModel {
	static final String NOTHING_SELECTED_MARKER = "---";
	static final String SUBDIVISION_LABEL_KEY = "addressform.subdivision.label";
	
	private ObservableList<String> countries;
	private ObservableList<String> subdivisions;
	
	private final ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper(true);
	private final ReadOnlyStringWrapper subdivisionLabel = new ReadOnlyStringWrapper();
	
	private final StringProperty street = new SimpleStringProperty();
	private final StringProperty postalCode = new SimpleStringProperty();
	private final StringProperty city = new SimpleStringProperty();
	private final ObjectProperty<Subdivision> subdivision = new SimpleObjectProperty<>();
	private final ObjectProperty<Country> country = new SimpleObjectProperty<>();
	
	private final StringProperty selectedCountry = new SimpleStringProperty(NOTHING_SELECTED_MARKER);
	private final StringProperty selectedSubdivision = new SimpleStringProperty(NOTHING_SELECTED_MARKER);
	
	private final ReadOnlyBooleanWrapper loadingInProgress = new ReadOnlyBooleanWrapper();
	private final ReadOnlyBooleanWrapper countryInputDisabled = new ReadOnlyBooleanWrapper();
	private final ReadOnlyBooleanWrapper subdivisionInputDisabled = new ReadOnlyBooleanWrapper();
	
	@Inject
	CountrySelector countrySelector;
	
	@Inject
	ResourceBundle resourceBundle;
	
	@InjectScope
	ContactDialogScope dialogScope;
	
	// Don't inline this field. It's needed to prevent the list mapping from being garbage collected.
	private ItemList<Country> countryItemList;
	// Don't inline this field. It's needed to prevent the list mapping from being garbage collected.
	private ItemList<Subdivision> subdivisionItemList;
	private Address address;
	
	private ObjectBinding<Contact> contactBinding;
	
	public void initialize() {
		dialogScope.subscribe(ContactDialogScope.RESET_FORMS, (key, payload) -> resetForm());
		dialogScope.subscribe(ContactDialogScope.COMMIT, (key, payload) -> commitChanges());
		
		ObjectProperty<Contact> contactToEditProperty = dialogScope.contactToEditProperty();
		
		if (contactToEditProperty.get() != null) {
			initWithAddress(contactToEditProperty.get().getAddress());
		}
		
		contactToEditProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (newValue.getAddress() == null) {
					System.out.println("Address is null");
				} else {
					initWithAddress(newValue.getAddress());
				}
			}
		});
		
		
		loadingInProgress.bind(countrySelector.inProgressProperty());
		countrySelector.init();
		
		initSubdivisionLabel();
		initCountryList();
		initSubdivisionList();
		
		selectedCountry.addListener((obs, oldV, newV) -> {
			if (newV != null && !newV.equals(NOTHING_SELECTED_MARKER)) {
				Optional<Country> matchingCountry = countrySelector.availableCountries().stream()
						.filter(country -> newV.equals(country.getName()))
						.findFirst();
						
				if (matchingCountry.isPresent()) {
					countrySelector.setCountry(matchingCountry.get());
					country.set(matchingCountry.get());
				}
			} else if (NOTHING_SELECTED_MARKER.equals(newV)) {
				countrySelector.setCountry(null);
				country.set(null);
			}
			selectedSubdivision.set(NOTHING_SELECTED_MARKER);
		});
		
		selectedSubdivision.addListener((obs, oldV, newV) -> {
			if (newV != null && !newV.equals(NOTHING_SELECTED_MARKER)) {
				Optional<Subdivision> subdivisionOptional = countrySelector.subdivisions().stream()
						.filter(subdivision -> subdivision.getName().equals(newV)).findFirst();
						
				if (subdivisionOptional.isPresent()) {
					subdivision.set(subdivisionOptional.get());
				} else {
					subdivision.set(null);
				}
			} else {
				subdivision.set(null);
			}
		});
		
		countryInputDisabled.bind(loadingInProgress);
		subdivisionInputDisabled.bind(loadingInProgress.or(Bindings.size(subdivisionsList()).lessThanOrEqualTo(1)));
		
		
		dialogScope.addressFormValidProperty().bind(valid);
	}
	
	void initSubdivisionLabel() {
		subdivisionLabel.bind(
				Bindings.when(
						countrySelector.subdivisionLabel().isEmpty())
						.then(resourceBundle.getString(SUBDIVISION_LABEL_KEY))
						.otherwise(countrySelector.subdivisionLabel()));
	}
	
	private void initSubdivisionList() {
		subdivisionItemList = new ItemList<>(countrySelector.subdivisions(), Subdivision::getName);
		subdivisions = createListWithNothingSelectedMarker(subdivisionItemList.getTargetList());
		subdivisions.addListener((ListChangeListener<String>) c -> selectedSubdivision.set(NOTHING_SELECTED_MARKER));
	}
	
	private void initCountryList() {
		countryItemList = new ItemList<>(countrySelector.availableCountries(), Country::getName);
		ObservableList<String> mappedList = countryItemList.getTargetList();
		
		countries = createListWithNothingSelectedMarker(mappedList);
		countries.addListener((ListChangeListener<String>) c -> selectedCountry.set(NOTHING_SELECTED_MARKER));
	}
	
	
	private void commitChanges() {
		address.setStreet(street.get());
		address.setCity(city.get());
		address.setPostalcode(postalCode.get());
		address.setCountry(country.get());
		address.setSubdivision(subdivision.get());
	}
	
	public void initWithAddress(Address address) {
		this.address = address;
		street.set(address.getStreet());
		city.set(address.getCity());
		postalCode.set(address.getPostalcode());
		
		if (address.getCountry() != null) {
			selectedCountry.set(address.getCountry().getName());
		}
		if (address.getSubdivision() != null) {
			selectedSubdivision.set(address.getSubdivision().getName());
		}
	}
	
	/**
	 * Creates an observable list that always has {@link #NOTHING_SELECTED_MARKER} as first element and the values of
	 * the given observable list.
	 */
	static ObservableList<String> createListWithNothingSelectedMarker(ObservableList<String> source) {
		final ObservableList<String> result = FXCollections.observableArrayList();
		result.add(NOTHING_SELECTED_MARKER);
		result.addAll(source);
		
		// for sure there are better solutions for this but it's sufficient for our demo
		source.addListener((ListChangeListener<String>) c -> {
			result.clear();
			result.add(NOTHING_SELECTED_MARKER);
			result.addAll(source);
		});
		return result;
	}
	
	
	public ObservableList<String> countriesList() {
		return countries;
	}
	
	public ObservableList<String> subdivisionsList() {
		return subdivisions;
	}
	
	public StringProperty streetProperty() {
		return street;
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
	public StringProperty postalCodeProperty() {
		return postalCode;
	}
	
	public StringProperty selectedCountryProperty() {
		return selectedCountry;
	}
	
	public StringProperty selectedSubdivisionProperty() {
		return selectedSubdivision;
	}
	
	public ReadOnlyStringProperty subdivisionLabel() {
		return subdivisionLabel.getReadOnlyProperty();
	}
	
	public ReadOnlyBooleanProperty loadingInProgressProperty() {
		return loadingInProgress.getReadOnlyProperty();
	}
	
	public ReadOnlyBooleanProperty countryInputDisabledProperty() {
		return countryInputDisabled.getReadOnlyProperty();
	}
	
	public ReadOnlyBooleanProperty subdivisionInputDisabledProperty() {
		return subdivisionInputDisabled.getReadOnlyProperty();
	}
	
	private void resetForm() {
		street.set("");
		city.set("");
		postalCode.set("");
		selectedCountry.set(NOTHING_SELECTED_MARKER);
		selectedSubdivision.set(NOTHING_SELECTED_MARKER);
		subdivision.set(null);
		country.set(null);
	}
}
