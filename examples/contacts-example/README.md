# MvvmFX Contacts Example

This is a more complex example application in which you can manage contacts.

It uses **mvvmFX** and **mvvmFX-CDI**. Additionally the following third-party libraries are used:

- [FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx) for the icons
- [ControlsFX](http://fxexperience.com/controlsfx/) for the validation decorators
- [AssertJ-JavaFX](https://github.com/lestard/assertj-javafx) for easier testing of observable values in unit tests
- [DataFX](http://www.javafxdata.org/) for loading XML files
- [Advanced-Bindings](https://github.com/lestard/advanced-bindings) to simplify some bindings
- [JFX-Testrunner](https://github.com/sialcasa/jfx-testrunner) to run Tests in the JavaFX Application thread

### The Use-Case

The application has a **master-detail** view. In the master pane there is a table of all contacts.
When one contact is selected, the detail view will show the properties of the selected contact.

With a dialog you can add new contacts or edit existing ones.



### Highlights and interesting parts

#### Usage of Scopes

[mvvmFX Scopes](https://github.com/sialcasa/mvvmFX/wiki/Scopes) are used for two scenarios in this example:

* Communication between the Master and the Detail View
* Dialog to add or edit a contact

##### Scope for Master Detail View


[MasterDetailScope.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/scopes/MasterDetailScope.java)

[MasterViewModel.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/master/MasterViewModel.java)

[DetailViewModel.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/detail/DetailViewModel.java)

```Java
public class MasterDetailScope implements Scope {
	private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>(this, "selectedContact");
}
```

```Java
public class MasterViewModel implements ViewModel {
	@InjectScope
	MasterDetailScope mdScope;
	
	public void initialize() {
		mdScope.selectedContactProperty().bind(selectedContact);
	}
}
```

```Java
public class MasterDetailScope implements Scope {
	private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>(this, "selectedContact");
}
```


##### Scope for Dialog Wizard

In this case the scope is used to handle the state of a multi paged wizard dialog.

###### Classes that are opening the dialogs

[ToolbarView.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/toolbar/ToolbarView)

[DetailView](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/detail/DetailView.java)

First usage of the scope in [DetailViewModel.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/detail/DetailViewModel), where the person object which should get edited is set.



###### Dialog Base

[ContactDialogViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactdialog/ContactDialogViewModel.java)

###### Specific Dialog Implementation

[EditContactDialogViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/editcontact/EditContactDialogViewModel.java)

[AddContactDialogViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/addcontact/AddContactDialogViewModel.java)

###### Dialog pages

[ContactFormViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactform/ContactFormViewModel.java)

[AddressFormViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/addressform/AddressFormViewModel.java)




#### ResourceBundles and I18N

There are resourceBundles available for german and english language. In [App.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/App.java)
a global resourceBundle is defined for the whole application:

```java
...

@Inject
private ResourceBundle resourceBundle;
	

@Override
public void startMvvmfx(Stage stage) throws Exception {
    LOG.info("Starting the Application");
    MvvmFX.setGlobalResourceBundle(resourceBundle);
    
    ...
}
```

In addition for the menu a specific resourceBundle is defined in the [MainView.fxml](src/main/resources/de/saxsys/mvvmfx/examples/contacts/ui/main/MainView.fxml) via `fx:include`:

```xml
...
<fx:include source="../menu/MenuView.fxml" resources="menu"/>
...
```

This resourceBundle is merged internally with the global resourceBundle so that the menu can access both resources. 


#### Validation

In the dialog for adding/editing contacts the mvvmFX validation feature is used. 
In [ContactFormViewModel.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactform/ContactFormViewModel.java) you can see
how to define validation logic. 
In the [ContactFormView.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactform/ContactFormView.java) the connection to the UI is done. 
This way the aspects of validation logic and validation visualization are separated.


#### Model-Wrapper

In the [ContactFormViewModel.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactform/ContactFormViewModel.java)
the mvvmFX ModelWrapper is used to connect the Model and the ViewModel layers with reduced code size and coupling.



