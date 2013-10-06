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
### Base implementations ###

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
### Notifications ###
__de.saxsys.jfx.mvvm.notifications__

This package provides an observer mechanism that you can use to send notifications which are identified by a string throught all of the application. You can pass objects with an notification.


#####Listen for a notification####
```
NotificationCenter notificationCenter = NotificationCenter.getDefaultNotificationCenter();

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
NotificationCenter notificationCenter = NotificationCenter.getDefaultNotificationCenter();

notificationCenter.postNotification("someNotification");

notificationCenter.postNotification("someNotification","arg1",new CustomArgTwo());
```

#### Extended FXML Loader ####
__de.saxsys.jfx.mvvm.viewloader__

The __ViewLoader__ loads tuples of a given FXML file. The tuple carries the code behind part (controller class) and the loaded __node__ which is represented by the FXML.

You have two options to use the __ViewLoader__
##### Use ViewLoader with resource path#####
```
ViewLoader.loadViewTuple("resource/path/to/FXML")
```
##### Use ViewLoader by a given code behind class#####
The FXML File which is related to the code behind part has to be in the same package and has to have the same name like the code behind!

If the code behind class is __de.saxsys.PersonView__ the FXML has to be in the package __de.saxsys__ and has to be named __PersonView.fxml__.

```
//CodeBehind implements Initializable
ViewLoader.loadViewTuple(CodeBehind.class);
```
