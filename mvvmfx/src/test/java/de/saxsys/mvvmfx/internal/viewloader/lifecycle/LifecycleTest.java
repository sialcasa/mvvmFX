package de.saxsys.mvvmfx.internal.viewloader.lifecycle;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.SceneLifecycle;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic.LifecycleTestRootView;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic.LifecycleTestRootViewModel;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic.LifecycleTestSub1ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic.LifecycleTestSub2ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc.LifecycleGCTestRootView;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc.LifecycleGCTestRootViewModel;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc.LifecycleGCTestSub1ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc.LifecycleGCTestSub2ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_notification.LifecycleNotificationView;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_notification.LifecycleNotificationViewModel;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_notification_without_lifecycle.NotificationWithoutLifecycleView;
import de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_notification_without_lifecycle.NotificationWithoutLifecycleViewModel;
import de.saxsys.mvvmfx.testingutils.FxTestingUtils;
import de.saxsys.mvvmfx.testingutils.GCVerifier;
import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import de.saxsys.mvvmfx.utils.notifications.DefaultNotificationCenter;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenterFactory;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(JfxToolkitExtension.class)
public class LifecycleTest {

	/**
	 * This test shows that the lifecycle methods are invoked
	 * for the root-viewModel and sub-viewModels.
	 *
	 * In this test no Garbage Collection is triggered.
	 * In the test case {@link #testLifecycleWithSubViewsWithGC()}
	 * the same workflow is tested with the only difference that
	 * garbage collection is enforced which produces a different behaviour
	 * of the lifecycle.
	 */
	@Test
	public void testLifecycleWithSubViewsWithoutGC() {
		FxTestingUtils.runInFXThread(() -> {
			LifecycleTestRootViewModel.onViewAddedCalled = 0;
			LifecycleTestRootViewModel.onViewRemovedCalled = 0;
			LifecycleTestSub1ViewModel.onViewAddedCalled = 0;
			LifecycleTestSub1ViewModel.onViewRemovedCalled = 0;
			LifecycleTestSub2ViewModel.onViewAddedCalled = 0;
			LifecycleTestSub2ViewModel.onViewRemovedCalled = 0;

			ViewTuple<LifecycleTestRootView, LifecycleTestRootViewModel> viewTuple = FluentViewLoader
					.fxmlView(LifecycleTestRootView.class).load();

			// the root view is not directly added to the Scene but encapsulated in
			// another container
			VBox subContainer = new VBox();
			subContainer.getChildren().add(viewTuple.getView());

			VBox container = new VBox();

			Stage stage = new Stage();
			Scene scene = new Scene(container);
			stage.setScene(scene);


			// before adding to scene
			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(0);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

			// add rootView to container
			container.getChildren().add(subContainer);

			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

			// remove from container
			container.getChildren().clear();

			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);


			// add again to container
			container.getChildren().add(subContainer);

			// the lifecycle methods are invoked again
			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(2);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(2);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(2);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);
		});
	}


	/**
	 * This test case scenario is similar to {@link #testLifecycleWithSubViewsWithoutGC()}
	 * with the only difference that this time garbage collection is enforeced
	 * on every relevant intermediate step.
	 * The expected behavior is that the viewModel isn't garbage collected until after
	 * the {@link SceneLifecycle#onViewRemoved()} method is invoked.
	 * This way the the user can use this method to cleanup other resources.
	 * However, after this method is invoked, no guarantee is given by the framework
	 * that the viewModel is still active. This means that the ViewModel will be collected
	 * by GC and therefore the {@link SceneLifecycle#onViewAdded()} is not invoked again
	 * when the view is added to the scene a second time.
	 */
	@Test
	public void testLifecycleWithSubViewsWithGC() {
		FxTestingUtils.runInFXThread(() -> {
			LifecycleTestRootViewModel.onViewAddedCalled = 0;
			LifecycleTestRootViewModel.onViewRemovedCalled = 0;
			LifecycleTestSub1ViewModel.onViewAddedCalled = 0;
			LifecycleTestSub1ViewModel.onViewRemovedCalled = 0;
			LifecycleTestSub2ViewModel.onViewAddedCalled = 0;
			LifecycleTestSub2ViewModel.onViewRemovedCalled = 0;

			ViewTuple<LifecycleTestRootView, LifecycleTestRootViewModel> viewTuple = FluentViewLoader
					.fxmlView(LifecycleTestRootView.class).load();

			// GC is performed, however as we still have a reference to the viewTuple,
			// nothing will be collected yet.
			GCVerifier.forceGC();

			// the root view is not directly added to the Scene. Instead it is encapsulated in
			// another container
			VBox subContainer = new VBox();
			subContainer.getChildren().add(viewTuple.getView());

			VBox container = new VBox();

			Stage stage = new Stage();
			Scene scene = new Scene(container);
			stage.setScene(scene);


			// now we clear the viewTuple reference ...
			viewTuple = null;

			// and perform GC a second time.
			// This time, both the ViewModel and the CodeBehind would be collected
			// if the framework hadn't prevented it.
			GCVerifier.forceGC();

			// before adding to scene
			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(0);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

			// add rootView to container
			container.getChildren().add(subContainer);

			// onViewAdded is invoked for all viewModels
			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

			GCVerifier.forceGC();

			// remove from container
			container.getChildren().clear();

			GCVerifier.forceGC();

			// onViewRemoved is invoked on all ViewModels
			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);

			// Here the guarantee of the framework ends. This time the viewModels
			// will be collected
			GCVerifier.forceGC();

			// add again to container
			container.getChildren().add(subContainer);

			// no methods are invoked.
			assertThat(LifecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
			assertThat(LifecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);
		});
	}


	/**
	 * This test scenario shows a use case were no lifecycle is used and
	 * a memory leak is produced.
	 * The test case {@link #testGarbageCollection()} shows the same
	 * example but with the usage of the lifecycle which prevents the memory leak.
	 * <p/>
	 * The ViewModel in this example subscribes to the notification center which
	 * leads to the notification center holding a reference of the ViewModel which
	 * prevents Garbage collection.
	 */
	@Test
	public void testGarbageCollectionFailed() {
		FxTestingUtils.runInFXThread(() -> {
			NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());

			ViewTuple<NotificationWithoutLifecycleView, NotificationWithoutLifecycleViewModel> viewTuple = FluentViewLoader
					.fxmlView(NotificationWithoutLifecycleView.class).load();

			VBox container = new VBox();

			Stage stage = new Stage();
			Scene scene = new Scene(container);
			stage.setScene(scene);

			GCVerifier vmVerifier = GCVerifier.create(viewTuple.getViewModel());

			assertThat(vmVerifier.isAvailableForGC()).isFalse();

			container.getChildren().add(viewTuple.getView());

			viewTuple = null;

			// The ViewModel has a listener subscribed so it isn't available for GC
			assertThat(vmVerifier.isAvailableForGC()).isFalse();

			container.getChildren().clear();

			// even after the view isn't used anymore, the ViewModel still can't be garbage collected
			// because it is still registered in the notification center
			assertThat(vmVerifier.isAvailableForGC()).isFalse();


			// only if we replace the notification center ...
			NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());


			// the viewModel can be garbage collected. Of cause in practice this isn't a suitable solution
			assertThat(vmVerifier.isAvailableForGC()).isTrue();
		});
	}

	/**
	 * This test case shows a typical use case of the lifecycle methods.
	 * The test case {@link #testGarbageCollectionFailed()} shows
	 * the same use case but without the usage of lifecycle methods which leads to a memory leak.
	 * In this test case this memory leak is prevented.
	 * <p/>
	 * The ViewModel subscribes to the notificationCenter in it's "initialize" method.
	 * In the {@link SceneLifecycle#onViewRemoved()} this notification is unsubscribed.
	 * Therefore the ViewModel is available for garbage collection afterwards.
	 */
	@Test
	public void testGarbageCollection() {
		FxTestingUtils.runInFXThread(() -> {
			NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());

			ViewTuple<LifecycleNotificationView, LifecycleNotificationViewModel> viewTuple = FluentViewLoader
					.fxmlView(LifecycleNotificationView.class).load();

			VBox container = new VBox();

			Stage stage = new Stage();
			Scene scene = new Scene(container);
			stage.setScene(scene);

			GCVerifier vmVerifier = GCVerifier.create(viewTuple.getViewModel());

			assertThat(vmVerifier.isAvailableForGC()).isFalse();

			container.getChildren().add(viewTuple.getView());

			viewTuple = null;

			// The ViewModel has a listener subscribed so it isn't available for GC now
			assertThat(vmVerifier.isAvailableForGC()).isFalse();

			// this triggeres the lifecycle method which is used to deregister the listener
			container.getChildren().clear();


			// therefore the ViewModel can now be garbage collected.
			assertThat(vmVerifier.isAvailableForGC()).isTrue();


			// cleanup notification center to not infer with other tests
			NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());
		});
	}


	/**
	 * This test scenario reproduces a misbehavior of the
	 * first prove-of-concept implementation of the lifecycle handling:
	 * When multiple ViewModels in a view hierarchy implement the {@link SceneLifecycle}
	 * interface, all viewModel's {@link SceneLifecycle#onViewRemoved()} have to be
	 * invoked when the root view is removed from the scene.
	 * The framework guarantees that all methods are invoked before the ViewModels become
	 * available for garbage collection.
	 * In the first implementation this guarantee wasn't fulfilled when garbage collection happens
	 * between two invocations of {@link SceneLifecycle#onViewRemoved()}.
	 * After the first lifecycle method was invoked all following viewModels could be collected
	 * by the Garbage collector.
	 * <p/>
	 *
	 * To reproduce this wrong behavior the viewModels in this test case are invoking
	 * {@link GCVerifier#forceGC()} inside of the {@link SceneLifecycle#onViewRemoved()} methods.
	 * In the previous implementation this resulted in only the first method was invoked.
	 */
	@Test
	public void testGcBetweenLifecycleMethods() {
		FxTestingUtils.runInFXThread(() -> {
			LifecycleGCTestRootViewModel.onViewRemovedCalled = 0;
			LifecycleGCTestSub1ViewModel.onViewRemovedCalled = 0;
			LifecycleGCTestSub2ViewModel.onViewRemovedCalled = 0;


			ViewTuple<LifecycleGCTestRootView, LifecycleGCTestRootViewModel> viewTuple = FluentViewLoader
					.fxmlView(LifecycleGCTestRootView.class).load();

			VBox subContainer = new VBox();
			subContainer.getChildren().add(viewTuple.getView());

			VBox container = new VBox();

			Stage stage = new Stage();
			Scene scene = new Scene(container);
			stage.setScene(scene);

			viewTuple = null;

			GCVerifier.forceGC();

			assertThat(LifecycleGCTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleGCTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
			assertThat(LifecycleGCTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

			container.getChildren().add(subContainer);

			GCVerifier.forceGC();

			container.getChildren().remove(subContainer);

			assertThat(LifecycleGCTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleGCTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
			assertThat(LifecycleGCTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);
		});
	}
}
