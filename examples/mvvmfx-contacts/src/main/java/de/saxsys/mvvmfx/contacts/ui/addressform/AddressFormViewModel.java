package de.saxsys.mvvmfx.contacts.ui.addressform;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Country;
import de.saxsys.mvvmfx.contacts.model.FederalState;
import de.saxsys.mvvmfx.contacts.model.Repository;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.print.DocFlavor;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AddressFormViewModel implements ViewModel {
	private static final String NOTHING_SELECTED_MARKER = "---";
	
	private ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper(true);
	private ObservableList<String> countries = FXCollections.observableArrayList();
	
	private ObservableList<String> federalStates = FXCollections.observableArrayList();
	
	private ObservableMap<String, List<String>> map = FXCollections.observableHashMap();
	
	private StringProperty street = new SimpleStringProperty();
	private StringProperty postalCode = new SimpleStringProperty();
	private StringProperty city= new SimpleStringProperty();
	private StringProperty selectedCountry = new SimpleStringProperty(NOTHING_SELECTED_MARKER);
	private StringProperty selectedFederalState = new SimpleStringProperty(NOTHING_SELECTED_MARKER);
	
	@Inject
	Repository repository;
	
	
	@PostConstruct
	public void init(){
		Set<Country> allCountries = repository.findAllCountries();
		
		countries.clear();
		countries.add(NOTHING_SELECTED_MARKER);
		
		selectedCountry.addListener((obs, oldV, newV) -> {
			
			
			
			federalStates.clear();
			federalStates.add(NOTHING_SELECTED_MARKER);
			selectedFederalState.set(NOTHING_SELECTED_MARKER);
			if (newV != null && !newV.equals(NOTHING_SELECTED_MARKER)) {
				Optional<Country> matchingCountry = allCountries.stream().filter(country -> newV.equals(country.getName()))
						.findFirst();
				if(matchingCountry.isPresent()){
					federalStates.addAll(matchingCountry.get().getFederalStates().stream().map(FederalState::getName).collect(Collectors.toList()));
				}
			}
		});

		countries.addAll(allCountries.stream().map(Country::getName).collect(Collectors.toList()));
		
		federalStates.add(NOTHING_SELECTED_MARKER);
	}
	

	public ReadOnlyBooleanProperty validProperty() {
		return valid.getReadOnlyProperty();
	}
	
	
	public ObservableList<String> countriesList(){
		return countries;
	}
	
	public ObservableList<String> federalStatesList(){
		return federalStates;
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
	
	public StringProperty selectedFederalStateProperty(){
		return selectedFederalState;
	}
}
