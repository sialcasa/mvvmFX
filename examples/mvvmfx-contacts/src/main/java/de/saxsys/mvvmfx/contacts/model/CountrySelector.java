package de.saxsys.mvvmfx.contacts.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import org.datafx.provider.ListDataProvider;
import org.datafx.reader.FileSource;
import org.datafx.reader.converter.XmlConverter;

import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CountrySelector {

	public static final String ISO_3166_LOCATION = "/countries/iso_3166.xml";
	private ObservableList<Country> countries = FXCollections.observableArrayList();
	private ObservableList<Subdivision> subdivisions = FXCollections.observableArrayList();
	
	
	private ReadOnlyStringWrapper subdivisionLabel = new ReadOnlyStringWrapper();
	
	private ReadOnlyBooleanWrapper inProgress = new ReadOnlyBooleanWrapper(false);

	
	public void loadCountries(){
		inProgress.set(true);
		URL iso3166Resource = this.getClass().getResource(ISO_3166_LOCATION);
		if(iso3166Resource == null){
			throw new IllegalStateException("Can't find the list of countries! Expected location was:" + ISO_3166_LOCATION);
		}

		XmlConverter<Country> countryConverter = new XmlConverter<>("iso_3166_entry",Country.class);

		try {
			FileSource<Country> dataSource = new FileSource<>(new File(iso3166Resource.getFile()),countryConverter);
			ListDataProvider<Country> listDataProvider = new ListDataProvider<>(dataSource);

			listDataProvider.setResultObservableList(countries);

			Worker<ObservableList<Country>> worker = listDataProvider.retrieve();
			worker.stateProperty().addListener(obs -> {
				if (worker.getState() == Worker.State.SUCCEEDED) {
					inProgress.set(false);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<Country> availableCountries(){
		return countries;
	}
	
	public void setCountry(Country country){
		
		
	}
	
	public ReadOnlyStringProperty subdivisionLabel(){
		return subdivisionLabel.getReadOnlyProperty();
	}
	
	public ObservableList<Subdivision> subdivisions(){
		return FXCollections.unmodifiableObservableList(subdivisions);
	}

	public ReadOnlyBooleanProperty inProgressProperty(){
		return inProgress.getReadOnlyProperty();
	}
}