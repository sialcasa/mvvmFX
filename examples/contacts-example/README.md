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

In this scenario the [MasterDetailScope](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/scopes/MasterDetailScope.java) is used to provide a property where the [MasterViewModel.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/master/MasterViewModel.java) signals which contact should be displayed in the [DetailViewModel.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/detail/DetailViewModel.java).

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
public class DetailViewModel implements ViewModel {
	@InjectScope
	MasterDetailScope mdScope;
	
	...//Create all bindings to the information of the mdScope.selectedContactProperty()
}

```


##### Scope for Dialog Wizard

In this case the scope is used to handle the state of a multi paged dialog.

To understand the machanism of the implemented dialogs, you should check the following classes: 

* [EditContactDialogView](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/editcontact/EditContactDialog.java) and [AddContactDialog](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/addcontact/AddContactDialog.java)

* Both of them are using the [ContactDialogView](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactdialog/ContactDialogView.java) with different configurations. 

* [AddressFormViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/addressform/AddressFormViewModel.java) and [ContactFormViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactform/ContactFormViewModel.java) are the dialog pages (1 and 2) that are displayed in the EditContactDialog and the AddContactDialog

* Also check the Views where the dialogs are created ([ToolbarView.java](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/toolbar/ToolbarView) and [DetailView](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/detail/DetailView.java)).

######Scope usages######
The used scope is called [ContactDialogScope](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/scopes/ContactDialogScope.java) and it has three use cases:

1. Configuration (eg. title) of the [ContactDialogViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactdialog/ContactDialogViewModel.java) from the [EditContactDialogViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/editcontact/EditContactDialogViewModel.java) and [AddContactDialogViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/addcontact/AddContactDialogViewModel.java).

2. [DetailViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/detail/DetailViewModel) sets the Contact object that will be edited into the scope. This information is used by the dialog pages: [AddressFormViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/addressform/AddressFormViewModel.java) and [ContactFormViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactform/ContactFormViewModel.java)    

3. [ContactDialogViewModel](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/contactdialog/ContactDialogViewModel.java) binds the *disableProperty()* of the navigation buttons to the validation state in the scope. This validation state is bound to the validation state of the dialog pages (AddressFormView and ContactFormView).


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



