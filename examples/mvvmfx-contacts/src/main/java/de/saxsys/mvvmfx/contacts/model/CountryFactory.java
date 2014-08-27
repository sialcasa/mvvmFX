package de.saxsys.mvvmfx.contacts.model;

public class CountryFactory {
	
	/**
	 * Creates "germany" as {@link de.saxsys.mvvmfx.contacts.model.Country} instance with all its federal states.
	 */
	public static Country createGermany(){
		Country germany = new Country("Deutschland");

		germany.addFederalState("Baden-Württemberg", "BW");
		germany.addFederalState("Bayern", "BY");
		germany.addFederalState("Berlin", "BE");
		germany.addFederalState("Brandenburg", "BB");
		germany.addFederalState("Bremen", "HB");

		germany.addFederalState("Hamburg", "HH");
		germany.addFederalState("Hessen", "HE");
		germany.addFederalState("Mecklemburg-Vorpommern", "MV");
		germany.addFederalState("Niedersachsen", "NI");
		germany.addFederalState("Nordrhein-Westfalen", "NW");

		germany.addFederalState("Rheinland-Pfalz", "RP");
		germany.addFederalState("Saarland", "SL");
		germany.addFederalState("Sachsen", "SN");
		germany.addFederalState("Sachsen-Anhalt", "ST");
		germany.addFederalState("Schleswig-Holstein", "SH");

		germany.addFederalState("Thüringen", "TH");

		return germany;
	}

	/**
	 * Creates "Austria" as {@link de.saxsys.mvvmfx.contacts.model.Country} instance with all its federal states.
	 */
	public static Country createAustria(){
		Country austria = new Country("Österreich");
		
		austria.addFederalState("Burgenland", "Bgld.");
		austria.addFederalState("Kärnten", "Ktn.");
		austria.addFederalState("Niederösterreich", "NÖ");
		austria.addFederalState("Oberösterreich", "OÖ");
		austria.addFederalState("Salzburg", "Sbg.");
		
		austria.addFederalState("Steiermark", "Stmk.");
		austria.addFederalState("Tirol", "T");
		austria.addFederalState("Vorarlberg", "Vbg.");
		austria.addFederalState("Wien", "W");
		
		return austria;
	}
}
