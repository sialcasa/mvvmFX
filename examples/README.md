### Contains the examples for mvvmfx

Content:

- **mvvmfx-complex-example**: contains the user interface and logic code for an example application.
To run this example you need either mvvmfx-cdi-starter or mvvmfx-guice-starter.
- **mvvmfx-cdi-starter**: contains the startup code to run the mvvmfx-complex-example with CDI/Weld
as dependency injection framework.
- **mvvmfx-guice-starter**: contains the startup code to run the mvvmfx-complex-example with Guice
as dependency injection framework. 
- **mvvmfx-fx-root-example**: contains a small custom control that uses the fx:root element together with mvvmfx.
- **mvvmfx-helloworld-example**: A simple hello world view. This example is used in the [Getting Started/Step-by-Step tutorial](/../../wiki/Getting-Started-HelloWorld-%28deutsch%29).
- **mvvmfx-helloworld-without-fxml**: A hello world example that shows hot to use MvvmFX with a view implemented in pure Java and not with FXML.
- **mvvmfx-contacts**: A contact management application. This example shows a master-detail view, dialogs and the usage of CDI including CDI-Events. 
This example also integrates some other JavaFX community libraries.
- **mvvmfx-synchronizefx**: This example uses the library [SynchronizeFX](https://github.com/saxsys/SynchronizeFX) to create a distributed ViewModel. 
This way the state of the UI of different instances of the App (on different JVM's, on different computers) is always synchronized between the apps. 