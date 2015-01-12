# MvvmFX Books Example

This example app is a client for a library REST service. You can search for books and view the
details of found books in a master-detail view.

![screenshot](screenshot.png)

This example was used for [a talk](http://www.jug-gr.de/2014/12/03/model-view-star.html) at the
[JavaUserGroup Görlitz](http://www.jug-gr.de) on [UI-Design patterns](https://github.com/lestard/model-view-star).

Originally [this app](https://github.com/sbley/hypermedia-library-client/tree/javafx) was created by [Stefan Bley](https://github.com/sbley) and
[Alexander Casall](https://github.com/sialcasa) for a talk on Hypermedia-APIs.
To run the app you need to have the [Hypermedia-API server](https://github.com/sbley/hypermedia-library-server)
up and running on `localhost` so that the client can make the necessary REST-Requests.


The app uses the following libraries (among others):

- [FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx) // Icons
- [FlatterFX](http://www.guigarage.com/javafx-themes/flatter/) // Styling
- [Advanced-Bindings](https://github.com/lestard/advanced-bindings) // Binding-Utils
- [EasyDI](https://github.com/lestard/EasyDI)  // Dependency-Injection
- [AssertJ-JavaFX](https://github.com/lestard/assertj-javafx)    // Testing
- [HALBuilder](https://github.com/HalBuilder)    // REST-Client