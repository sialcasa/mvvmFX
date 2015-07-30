### Contains the examples for mvvmfx

At the moment we have 3 example applications:
- **books-example**: An app to search for books in a library.
  - Shows how to integrate a REST backend (Optional)
  - Uses EasyDI as dependency injection library
- **contacts-example**: A contact management application
  - Master-Detail
  - Dialogs
  - CDI as dependency injection library, including CDI-Events
  - Validation
  - Model-Wrapper
  - I18N and ResourceBundle handling
  - [DataFX](http://www.javafxdata.org/)
- **todomvc-example**: A Todo-App influenced by the popular [TodoMVC.com](http://todomvc.com/).
  - NotificationCenter
  - MvvmFX views as items of a ListView
  - [EasyBind](https://github.com/TomasMikula/EasyBind) for filtering Lists


In addition to these apps we have some smaller examples. Each examples shows a specific aspect of the framework but isn't very useful by itself. 

- **fx-root-example**: Shows how to use `fx:root` with mvvmFX. This way you can create your own custom components.
- **helloworld**: A minimal mvvmFX application using *FXML*.
- **helloworld-without-fxml**: A minimal mvvmFX application using pure Java code instead of *FXML*.
- **synchronizefx-example**: Shows how to integrade the library [SynchronizeFX](https://github.com/saxsys/SynchronizeFX) to implement a distributed ViewModel. This way the state of the UI of different instances of the App (on different JVM's, on different computers) is always synchronized between the apps. 
- **welcome-example**: A simple app that shows a welcome message for people. 
    - It demonstrates the usage of [mvvmfx-cdi](https://github.com/sialcasa/mvvmFX/tree/develop/mvvmfx-cdi) and [mvvmfx-guice](https://github.com/sialcasa/mvvmFX/tree/develop/mvvmfx-guice). The complete code base is shared, only a specific starter class for each dependency injection framework is needed.
    - Shows the usage of [Commands](https://github.com/sialcasa/mvvmFX/wiki/Commands).
