package de.saxsys.mvvmfx.contacts.ui.detail;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.ui.master.MasterViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class DetailViewModel implements ViewModel {

	private ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();



	@Inject
	MasterViewModel masterViewModel;

	
	@PostConstruct
	public void init(){
		ReadOnlyObjectProperty<Contact> contactProperty = masterViewModel.selectedContactProperty();

		name.bind(Bindings.when(contactProperty.isNull()).then("").otherwise(Bindings.createStringBinding(() -> {
			Contact contact = contactProperty.get();
			StringBuilder result = new StringBuilder();
			
			if(contact != null){
				String title = contact.getTitle();
				if(title != null && !title.trim().isEmpty()){
					result.append(title);
					result.append(" ");
				}
				
				result.append(contact.getFirstname());
				result.append(" ");
				result.append(contact.getLastname());
			}
			
			return result.toString();
		}, contactProperty)));
	}

	public ReadOnlyStringProperty nameProperty() {
		return name.getReadOnlyProperty();
	}
}
