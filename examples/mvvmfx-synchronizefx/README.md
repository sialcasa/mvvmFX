# MvvmFX with SynchronizeFX

This example shows how you can combine mvvmFX with the framework [SynchronizeFX](https://github.com/saxsys/SynchronizeFX).

SynchronizeFX is a library for JavaFX that enables property bindings between different JVMs over the network.

This example consists of a server application (still a simple java main class) and
a client app that has a UI with a simple slider on it. When you start multiple
instances of the client and connect them to the server, the value of the slider will
be synchronized between all connected clients. We will archive this by synchronizing
the ViewModels of the applications.

To run this example you have to:

1. Start `de.saxsys.mvvmfx.examples.synchronize.ServerApp`.
2. Start one or more instances of `de.saxsys.mvvmfx.examples.synchronize.ClientApp`


What is special in this example is that the ViewModel isn't created by the mvvmFX framework.
Instead an instance is created in the `ServerApp` class and SynchronizeFX is used to obtain a
synchronized instance of the ViewModel on the client side.

This is done in the `modelReady` callback in `ClientApp`. Starting with version 0.3.0 of mvvmFX it is possible to use an existing ViewModel instance when
loading a view, which is used in this example.

```java
  client = SynchronizeFxBuilder.create().client().address(SERVER).callback(new ClientCallback() {
			@Override public void modelReady(Object object) {

				if (object instanceof SliderViewModel) {
					SliderViewModel viewModel = (SliderViewModel) object;

					ViewTuple<SliderView, SliderViewModel> viewTuple = FluentViewLoader.fxmlView(SliderView.class)
							.viewModel(viewModel).load();

					primaryStage.setScene(new Scene(viewTuple.getView(),400,200));

					primaryStage.show();
				}
			}
      ...
		}).build();
```


This example is based on a demo of SynchronizeFX. The pure JavaFX version of this example can be seen on the
[synchronizeFX github project](https://github.com/saxsys/SynchronizeFX/tree/master/demos/sliderdemo).
