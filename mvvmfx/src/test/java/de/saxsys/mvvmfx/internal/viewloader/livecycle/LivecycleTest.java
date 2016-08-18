package de.saxsys.mvvmfx.internal.viewloader.livecycle;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.SceneLivecycle;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_basic.LivecycleTestRootView;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_basic.LivecycleTestRootViewModel;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_basic.LivecycleTestSub1ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_basic.LivecycleTestSub2ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc.LivecycleGCTestRootView;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc.LivecycleGCTestRootViewModel;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc.LivecycleGCTestSub1ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc.LivecycleGCTestSub2ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_notification.LivecycleNotificationView;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_notification.LivecycleNotificationViewModel;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_notification_without_livecycle.NotificationWithoutLivecycleView;
import de.saxsys.mvvmfx.internal.viewloader.livecycle.example_notification_without_livecycle.NotificationWithoutLivecycleViewModel;
import de.saxsys.mvvmfx.testingutils.GCVerifier;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import de.saxsys.mvvmfx.testingutils.jfxrunner.TestInJfxThread;
import de.saxsys.mvvmfx.utils.notifications.DefaultNotificationCenter;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenterFactory;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class LivecycleTest {

	/**
	 * This test shows that the livecycle methods are invoked
	 * for the root-viewModel and sub-viewModels.
	 *
	 * In this test no Garbage Collection is triggered.
	 * In the test case {@link #testLivecycleWithSubViewsWithGC()}
	 * the same workflow is tested with the only difference that
	 * garbage collection is enforced which produces a different behaviour
	 * of the livecycle.
	 */
	@Test
	@TestInJfxThread
	public void testLivecycleWithSubViewsWithoutGC() {
		LivecycleTestRootViewModel.onViewAddedCalled = 0;
		LivecycleTestRootViewModel.onViewRemovedCalled = 0;
		LivecycleTestSub1ViewModel.onViewAddedCalled = 0;
		LivecycleTestSub1ViewModel.onViewRemovedCalled = 0;
		LivecycleTestSub2ViewModel.onViewAddedCalled = 0;
		LivecycleTestSub2ViewModel.onViewRemovedCalled = 0;

		ViewTuple<LivecycleTestRootView, LivecycleTestRootViewModel> viewTuple = FluentViewLoader.fxmlView(LivecycleTestRootView.class).load();

		// the root view is not directly added to the Scene but encapsulated in
		// another container
		VBox subContainer = new VBox();
		subContainer.getChildren().add(viewTuple.getView());

		VBox container = new VBox();

		Stage stage = new Stage();
		Scene scene = new Scene(container);
		stage.setScene(scene);


		// before adding to scene
		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

		// add rootView to container
		container.getChildren().add(subContainer);

		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

		// remove from container
		container.getChildren().clear();

		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);


		// add again to container
		container.getChildren().add(subContainer);

		// the livecycle methods are invoked again
		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(2);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(2);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(2);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);
	}


	/**
	 * This test case scenario is similar to {@link #testLivecycleWithSubViewsWithoutGC()}
	 * with the only difference that this time garbage collection is enforeced
	 * on every relevant intermediate step.
	 * The expected behavior is that the viewModel isn't garbage collected until after
	 * the {@link SceneLivecycle#onViewRemoved()} method is invoked.
	 * This way the the user can use this method to cleanup other resources.
	 * However, after this method is invoked, no guarantee is given by the framework
	 * that the viewModel is still active. This means that the ViewModel will be collected
	 * by GC and therefore the {@link SceneLivecycle#onViewAdded()} is not invoked again
	 * when the view is added to the scene a second time.
	 */
	@Test
	@TestInJfxThread
	public void testLivecycleWithSubViewsWithGC() {
		LivecycleTestRootViewModel.onViewAddedCalled = 0;
		LivecycleTestRootViewModel.onViewRemovedCalled = 0;
		LivecycleTestSub1ViewModel.onViewAddedCalled = 0;
		LivecycleTestSub1ViewModel.onViewRemovedCalled = 0;
		LivecycleTestSub2ViewModel.onViewAddedCalled = 0;
		LivecycleTestSub2ViewModel.onViewRemovedCalled = 0;

		ViewTuple<LivecycleTestRootView, LivecycleTestRootViewModel> viewTuple = FluentViewLoader.fxmlView(LivecycleTestRootView.class).load();

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
		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

		// add rootView to container
		container.getChildren().add(subContainer);

		// onViewAdded is invoked for all viewModels
		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

		GCVerifier.forceGC();

		// remove from container
		container.getChildren().clear();

		GCVerifier.forceGC();

		// onViewRemoved is invoked on all ViewModels
		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);

		// Here the guarantee of the framework ends. This time the viewModels
		// will be collected
		GCVerifier.forceGC();

		// add again to container
		container.getChildren().add(subContainer);

		// no methods are invoked.
		assertThat(LivecycleTestRootViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(LivecycleTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);
	}


	/**
	 * This test scenario shows a use case were no livecycle is used and
	 * a memory leak is produced.
	 * The test case {@link #testGarbageCollection()} shows the same
	 * example but with the usage of the livecylce which prevents the memory leak.
	 * <p/>
	 * The ViewModel in this example subscribes to the notification center which
	 * leads to the notification center holding a reference of the ViewModel which
	 * prevents Garbage collection.
	 */
	@Test
	@TestInJfxThread
	public void testGarbageCollectionFailed() {
		NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());

		ViewTuple<NotificationWithoutLivecycleView, NotificationWithoutLivecycleViewModel> viewTuple = FluentViewLoader.fxmlView(NotificationWithoutLivecycleView.class).load();

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
	}

	/**
	 * This test case shows a typical use case of the livecycle methods.
	 * The test case {@link #testGarbageCollectionFailed()} shows
	 * the same use case but without the usage of livecycle methods which leads to a memory leak.
	 * In this test case this memory leak is prevented.
	 * <p/>
	 * The ViewModel subscribes to the notificationCenter in it's "initialize" method.
	 * In the {@link SceneLivecycle#onViewRemoved()} this notification is unsubscribed.
	 * Therefore the ViewModel is available for garbage collection afterwards.
	 */
	@Test
	@TestInJfxThread
	public void testGarbageCollection() {
		NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());

		ViewTuple<LivecycleNotificationView, LivecycleNotificationViewModel> viewTuple = FluentViewLoader.fxmlView(LivecycleNotificationView.class).load();

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

		// this triggeres the livecycle method which is used to deregister the listener
		container.getChildren().clear();


		// therefore the ViewModel can now be garbage collected.
		assertThat(vmVerifier.isAvailableForGC()).isTrue();


		// cleanup notification center to not infer with other tests
		NotificationCenterFactory.setNotificationCenter(new DefaultNotificationCenter());
	}


	/**
	 * This test scenario reproduces a misbehavior of the
	 * first prove-of-concept implementation of the livecylce handling:
	 * When multiple ViewModels in a view hierarchy implement the {@link SceneLivecycle}
	 * interface, all viewModel's {@link SceneLivecycle#onViewRemoved()} have to be
	 * invoked when the root view is removed from the scene.
	 * The framework guarantees that all methods are invoked before the ViewModels become
	 * available for garbage collection.
	 * In the first implementation this guarantee wasn't fulfilled when garbage collection happens
	 * between two invocations of {@link SceneLivecycle#onViewRemoved()}.
	 * After the first livecycle method was invoked all following viewModels could be collected
	 * by the Garbage collector.
	 * <p/>
	 *
	 * To reproduce this wrong behavior the viewModels in this test case are invoking
	 * {@link GCVerifier#forceGC()} inside of the {@link SceneLivecycle#onViewRemoved()} methods.
	 * In the previous implementation this resulted in only the first method was invoked.
	 */
	@Test
	@TestInJfxThread
	public void testGcBetweenLivecycleMethods() {
		LivecycleGCTestRootViewModel.onViewRemovedCalled = 0;
		LivecycleGCTestSub1ViewModel.onViewRemovedCalled = 0;
		LivecycleGCTestSub2ViewModel.onViewRemovedCalled = 0;


		ViewTuple<LivecycleGCTestRootView, LivecycleGCTestRootViewModel> viewTuple = FluentViewLoader.fxmlView(LivecycleGCTestRootView.class).load();

		VBox subContainer = new VBox();
		subContainer.getChildren().add(viewTuple.getView());

		VBox container = new VBox();

		Stage stage = new Stage();
		Scene scene = new Scene(container);
		stage.setScene(scene);

		viewTuple = null;

		GCVerifier.forceGC();

		assertThat(LivecycleGCTestRootViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleGCTestSub1ViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(LivecycleGCTestSub2ViewModel.onViewRemovedCalled).isEqualTo(0);

		container.getChildren().add(subContainer);

		GCVerifier.forceGC();

		container.getChildren().remove(subContainer);

		assertThat(LivecycleGCTestRootViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleGCTestSub1ViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(LivecycleGCTestSub2ViewModel.onViewRemovedCalled).isEqualTo(1);
	}
}
