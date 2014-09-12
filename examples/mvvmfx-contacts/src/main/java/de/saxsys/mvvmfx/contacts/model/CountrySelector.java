package de.saxsys.mvvmfx.contacts.model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountrySelector {

	private ObservableList<Country> countries = FXCollections.observableArrayList();
	private ObservableList<Subdivision> subdivisions = FXCollections.observableArrayList();
	
	private Map<Country, List<Subdivision>> map = new HashMap<>();
	
	private ReadOnlyStringWrapper subdivisionLabel = new ReadOnlyStringWrapper();
	
	public CountrySelector(){
		Country germany = new Country("Deutschland", "DE");
		map.put(germany, new ArrayList<>());
		Country austria = new Country("Österreich", "AT");
		map.put(austria, new ArrayList<>());
		
		countries.addAll(map.keySet());

		map.get(austria).add(new Subdivision("Burgenland", "Bgld.", austria));
		map.get(austria).add(new Subdivision("Kärnten", "Ktn.", austria));
		map.get(austria).add(new Subdivision("Niederösterreich", "NÖ", austria));
		map.get(austria).add(new Subdivision("Oberösterreich", "OÖ", austria));
		map.get(austria).add(new Subdivision("Salzburg", "Sbg.", austria));

		map.get(austria).add(new Subdivision("Steiermark", "Stmk.", austria));
		map.get(austria).add(new Subdivision("Tirol", "T", austria));
		map.get(austria).add(new Subdivision("Vorarlberg", "Vbg.", austria));
		map.get(austria).add(new Subdivision("Wien", "W", austria));


		map.get(germany).add(new Subdivision("Baden-Württemberg", "BW", germany));
		map.get(germany).add(new Subdivision("Bayern", "BY", germany));
		map.get(germany).add(new Subdivision("Berlin", "BE", germany));
		map.get(germany).add(new Subdivision("Brandenburg", "BB", germany));
		map.get(germany).add(new Subdivision("Bremen", "HB", germany));

		map.get(germany).add(new Subdivision("Hamburg", "HH", germany));
		map.get(germany).add(new Subdivision("Hessen", "HE", germany));
		map.get(germany).add(new Subdivision("Mecklemburg-Vorpommern", "MV", germany));
		map.get(germany).add(new Subdivision("Niedersachsen", "NI", germany));
		map.get(germany).add(new Subdivision("Nordrhein-Westfalen", "NW", germany));

		map.get(germany).add(new Subdivision("Rheinland-Pfalz", "RP", germany));
		map.get(germany).add(new Subdivision("Saarland", "SL", germany));
		map.get(germany).add(new Subdivision("Sachsen", "SN", germany));
		map.get(germany).add(new Subdivision("Sachsen-Anhalt", "ST", germany));
		map.get(germany).add(new Subdivision("Schleswig-Holstein", "SH", germany));

		map.get(germany).add(new Subdivision("Thüringen", "TH", germany));
	}
	
	public ObservableList<Country> availableCountries(){
		return countries;
	}
	
	public void setCountry(Country country){
		subdivisions.clear();
		
		if(country != null){
			subdivisions.addAll(map.get(country));
			if(country.getCountryCode().equals("DE")){
				subdivisionLabel.set("Bundesland");
			}else if(country.getCountryCode().equals("AT")){
				subdivisionLabel.set("Bundesland");
			}
		}else{
			subdivisionLabel.set(null);
		}
	}
	
	public ReadOnlyStringProperty subdivisionLabel(){
		return subdivisionLabel.getReadOnlyProperty();
	}
	
	public ObservableList<Subdivision> subdivisions(){
		return subdivisions;
	}
	
}