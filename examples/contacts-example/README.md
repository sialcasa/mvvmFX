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

#### Dialogs opened with CDI-Events

- The application uses CDI-Events to decouple the *add*/*edit* dialogs from the places where they are opened. Instead, when a
 button is clicked to open a dialog, an CDI-Event is fired. The dialog reacts to this event and will open up itself.

[ToolbarViewModel.java:](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/toolbar/ToolbarViewModel.java)

```java
@Inject
private Event<OpenAddContactDialogEvent> openPopupEvent;

public void addNewContactAction(){
    openPopupEvent.fire(new OpenAddContactDialogEvent());
}
```

[AddContactDialog.java:](src/main/java/de/saxsys/mvvmfx/examples/contacts/ui/addcontact/AddContactDialog.java)

```java
public class AddContactDialog implements FxmlView<AddContactDialogViewModel> {
    ...

    public void open(@Observes OpenAddContactDialogEvent event) {
      viewModel.openDialog();
    }
}
```

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



