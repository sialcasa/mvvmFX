#mvvm(fx)#

__mvvm(FX)__ is an application framework for implementing the MVVM pattern with JavaFX. 

__MVVM__ is a WPF ( __.NET__ ) specific pattern. WPF and JavaFX does have parallels like Databinding and descriptive UI declaration (FXML/XAML). That is the reason I tried to adopt best practices of the development with WPF.

#####Short overview#####
You have 2 options to implement MVVM with JavaFX. It depents whether you want to use JavaFX-Properties in your model or not. 

If __no__, you should go for the first image.

<img src="http://buildpath.de/mvvm/mvvm.png" alt="Drawing" width="200px" style="width: 200px;padding-left:20px;"/>
<img src="http://buildpath.de/mvvm/mvvm_2.png" alt="Drawing" width="200px" style="width: 200px;padding-left:60px;"/>

If __yes__ go for the second image. If you use Properties in your model, you have the benefit of using their databinding and the observer mechanism.



#####Further information#####

[Short description](http://msdn.microsoft.com/en-us/library/hh848246.aspx "Short Description")

[Long description](http://msdn.microsoft.com/en-us/magazine/dd419663.aspx "Long Description")

#The Framework#
##How to implement##
<img src="http://buildpath.de/mvvm/mvvm_steps.png" alt="Drawing" style="width: 500px;padding:50px;"/>

##Parts##
###2 new Custom Application Type###
####__MvvmGuiceApplication__ instead of __javafx.Application__ for Guice support####

```
public class Starter extends MvvmGuiceApplication {

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
	}

	@Override
	public void initGuiceModules(List<Module> modules) throws Exception {
		//add Guice Modules if you want
	}

}
```
####__MvvmCdiApplication__ instead of __javafx.Application__ for CDI-Weld support####

```
public class Starter extends MvvmCdiApplication{
	
	public static void main(String...args){
		launch(args);
	}
}
```

in addition you have to provide an App class, which receives the StartUp Event of the weld-container

```
	public class App {

	// Get the MVVM View Loader
	@Inject
	private ViewLoader viewLoader;

	/**
	 * Listen for the {@link StartupEvent} and create the main scene for the
	 * application.
	 */
	public void startApplication(@Observes StartupEvent startupEvent) {
		Stage stage = startupEvent.getPrimaryStage();

		final ViewTuple tuple = viewLoader
				.loadViewTuple(MainContainerView.class);
		// Locate View for loaded FXML file
		final Parent view = tuple.getView();

		final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
		}
	}
}
```

### MVVM Base Classes ###

__de.saxsys.jfx.mvvm.base__

This package contains the base classes for a __View__ and a __ViewModel__. The __View__ is the code behind (Controller class) of a __FXML__ file and implements the interface __javafx.fxml.Initializable__. Every __View__ does have a reference to exactly one __ViewModel__ which is declared by using generics.

Example for a __View__ and the belonging __ViewModel__

```
public class PersonView extends View<PersonViewModel>{
...
}
```
__If you create View the belonging ViewModel will be created automatically. You can get the instance by using getViewModel() on the View Class__

```
public class PersonViewModel implements ViewModel{
...
}
```

__de.saxsys.jfx.mvvm.base.viewmodel.util__

__de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist__

This class provides a __ItemList__ which maps from an Object representation to a String. You can use this to map lists from the model to the view on an abstract way. In addition there is a __SelectableItemList__ which provides an addition funcitonality to select an index by setting the index value __OR__ an object in the object list.
__ incomplete documentation __ 

#### Extended FXML Loader ####
__de.saxsys.jfx.mvvm.viewloader__

The __ViewLoader__ loads tuples of a given FXML file. The tuple carries the code behind part (controller class) and the loaded __node__ which is represented by the FXML.

You have two options to use the __ViewLoader__
##### Use ViewLoader with resource path#####
```
@Inject
private ViewLoader viewLoader;

[…]

viewLoader.loadViewTuple("resource/path/to/FXML")
```
##### Use ViewLoader by a given code behind class#####
The FXML File which is related to the code behind part has to be in the corresponding resource folder and has to have the same name like the code behind!

If the code behind class is __de.saxsys.PersonView__ the FXML has to be in the resource folder __de/saxsys__ and has to be named __PersonView.fxml__.

```
@Inject
private ViewLoader viewLoader;

[…]

viewLoader.loadViewTuple(CodeBehind.class);
```

### Notifications ###
__de.saxsys.jfx.mvvm.notifications__

This package provides an observer mechanism that you can use to send notifications which are identified by a string throught all of the application. You can pass objects with an notification.


#####Listen for a notification####
```
@Inject
private NotificationCenter notificationCenter;

[...]

notificationCenter.addObserverForName("someNotification",
			new NotificationObserver() {
			
			@Override
			public void receivedNotification(String key, Object... objects) {
				System.out.println("Received Notification: "+ key + "With object count:" +objects.length);
			}
		}
	);
```
##### Send a notification #####
```
@Inject
private NotificationCenter notificationCenter;

[...]

notificationCenter.postNotification("someNotification");

notificationCenter.postNotification("someNotification","arg1",new CustomArgTwo());
```
### SizeBindings ###
__de.saxsys.jfx.mvvm.utils.SizeBindings__

We often had the case, that an element should have the same size or at least the width of another element. You would have to do it on this way:

```
a.minWidthProperty().bind(b.widthProperty());
a.minheightProperty().bind(b.heightProperty())
a.maxWidthProperty().bind(b.widthProperty());
a.maxheightProperty().bind(b.heightProperty())
```

With the SizeBindings class you can do instead things like

```
SizeBindings.bindSize(a,b);
```

### ListenerCleaner ###
__de.saxsys.jfx.mvvm.utils.ListenerCleaner__

This class is used for housekeeping of listeners. If you have an element which registers Listeners to a long living Property you have to remove the listener by hand when you want to throw the element away.

You can use the Interface ICleanable to make your element cleanable. You should call the clean function of the element, when you remove it from the scene graph / throw it away. 

```
public class CustomPane extends Pane implements ICleanable{

@Inject
ListenerCleaner cleaner;

	public CustomPane(SomeLongLiving longLiving){
		ChangeListener<Double> listener = new ChangeListener().......
	
		longLiving.fooProperty().addListener(listener);
		cleaner.put(longLiving.fooProperty(),listener);
	}
	
	@Override
	public void clean(){
		//Kills all Listeners
		cleaner.clean();
	}

}
```