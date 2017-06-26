package de.saxsys.mvvmfx.examples.contacts.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "iso_3166_2_entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class ISO3166_2_Entries {

    @XmlElement(name = "iso_3166_country")
    public ArrayList<ISO3166_2_CountryEntity> countryEntities;
    
}
