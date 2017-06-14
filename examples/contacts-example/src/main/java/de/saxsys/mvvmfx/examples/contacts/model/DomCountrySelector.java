package de.saxsys.mvvmfx.examples.contacts.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class DomCountrySelector implements CountrySelector {
	private static final Logger LOG = LoggerFactory.getLogger(DomCountrySelector.class);

	public static final String ISO_3166_LOCATION = "/countries/iso_3166.xml";
	public static final String ISO_3166_2_LOCATION = "/countries/iso_3166_2.xml";

	private ObservableList<Country> countries = FXCollections.observableArrayList();
	private ObservableList<Subdivision> subdivisions = FXCollections.observableArrayList();

	private ReadOnlyStringWrapper subdivisionLabel = new ReadOnlyStringWrapper();

	private ReadOnlyBooleanWrapper inProgress = new ReadOnlyBooleanWrapper(false);

	private Map<Country, List<Subdivision>> countryCodeSubdivisionMap = new HashMap<>();
	private Map<Country, String> countryCodeSubdivisionNameMap = new HashMap<>();

	@Override
	@PostConstruct
	public void init() {
		inProgress.setValue(true);
		loadCountries();
	}

	private void loadCountries() {
		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			loadCountriesFromXml(documentBuilder);
			loadSubdivisionsFromXml(documentBuilder);

		} catch (Exception e) {
			LOG.error("Cannot load Countries from XML file", e);
		}
		inProgress.setValue(false);
	}

	private void loadSubdivisionsFromXml(DocumentBuilder documentBuilder) throws SAXException, IOException {
		Document subdivisionsDocument = documentBuilder.parse(this.getClass().getResourceAsStream(ISO_3166_2_LOCATION));
		subdivisionsDocument.getDocumentElement().normalize();

		NodeList countryNodes = subdivisionsDocument.getElementsByTagName("iso_3166_country");
		for(int countryIndex=0 ; countryIndex<countryNodes.getLength() ; countryIndex++) {
			Node countryNode = countryNodes.item(countryIndex);
			String countryCode = countryNode.getAttributes().getNamedItem("code").getNodeValue();

			Country country = findCountryByCode(countryCode);
			if (!countryCodeSubdivisionMap.containsKey(country)) {
				countryCodeSubdivisionMap.put(country, new ArrayList<>());
			}

			List<Subdivision> subdivisionList = countryCodeSubdivisionMap.get(country);

			if(country != null) {
				NodeList subsetNodes = ((Element)countryNode).getElementsByTagName("iso_3166_subset");

				for(int subsetIndex=0 ; subsetIndex < subsetNodes.getLength() ; subsetIndex++) {
					Node subsetNode = subsetNodes.item(subsetIndex);

					String subsetType = subsetNode.getAttributes().getNamedItem("type").getNodeValue();

					countryCodeSubdivisionNameMap.put(country, subsetType);

					NodeList entryNodes = ((Element) subsetNode).getElementsByTagName("iso_3166_2_entry");

					for(int entryIndex=0 ; entryIndex<entryNodes.getLength() ; entryIndex++) {
						Node entryNode = entryNodes.item(entryIndex);

						String entryName = entryNode.getAttributes().getNamedItem("name").getNodeValue();
						String entryCode = entryNode.getAttributes().getNamedItem("code").getNodeValue();

						subdivisionList.add(new Subdivision(entryName, entryCode, country));
					}
				}
			}
		}
	}

	private void loadCountriesFromXml(DocumentBuilder documentBuilder) throws SAXException, IOException {
		Document countriesDocument = documentBuilder.parse(this.getClass().getResourceAsStream(ISO_3166_LOCATION));

		countriesDocument.getDocumentElement().normalize();
		NodeList entries = countriesDocument.getElementsByTagName("iso_3166_entry");
		for(int i=0; i<entries.getLength(); i++) {
			Node item = entries.item(i);
			String name = item.getAttributes().getNamedItem("name").getNodeValue();
			String alpha2Code = item.getAttributes().getNamedItem("alpha_2_code").getNodeValue();

			Country country = new Country(name, alpha2Code);

			countries.add(country);
		}
	}

	private Country findCountryByCode(String code) {
		return countries.stream().filter(country -> country.getCountryCode().equals(code)).findFirst().orElse(null);
	}


	@Override
	public void setCountry(Country country) {
		if (country == null) {
			subdivisionLabel.set(null);
			subdivisions.clear();
			return;
		}

		subdivisionLabel.set(countryCodeSubdivisionNameMap.get(country));

		subdivisions.clear();
		if (countryCodeSubdivisionMap.containsKey(country)) {
			subdivisions.addAll(countryCodeSubdivisionMap.get(country));
		}
	}

	@Override
	public ObservableList<Country> availableCountries() {
		return countries;
	}

	@Override
	public ReadOnlyStringProperty subdivisionLabel() {
		return subdivisionLabel.getReadOnlyProperty();
	}

	@Override
	public ObservableList<Subdivision> subdivisions() {
		return FXCollections.unmodifiableObservableList(subdivisions);
	}

	@Override
	public ReadOnlyBooleanProperty inProgressProperty() {
		return inProgress.getReadOnlyProperty();
	}
}
