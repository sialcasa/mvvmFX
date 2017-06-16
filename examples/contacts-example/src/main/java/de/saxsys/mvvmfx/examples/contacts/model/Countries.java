package de.saxsys.mvvmfx.examples.contacts.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "iso_3166_entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class Countries {
    
    @XmlElement(name = "iso_3166_entry")
    private ArrayList<Country> countries;

    public ArrayList<Country> getCountries() {
        return countries;
    }

}
