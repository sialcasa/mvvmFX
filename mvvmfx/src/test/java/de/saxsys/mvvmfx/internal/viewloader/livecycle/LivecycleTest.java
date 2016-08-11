package de.saxsys.mvvmfx.internal.viewloader.livecycle;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import de.saxsys.mvvmfx.testingutils.jfxrunner.TestInJfxThread;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class LivecycleTest {


	@Test
	@TestInJfxThread
	public void test() {
		ViewTuple<LivecycleTestRootView, LivecycleTestRootViewModel> viewTuple = FluentViewLoader.fxmlView(LivecycleTestRootView.class).load();
		LivecycleTestRootViewModel rootViewModel = viewTuple.getViewModel();

		LivecycleTestSub1ViewModel sub1ViewModel = viewTuple.getCodeBehind().sub1Controller.viewModel;
		LivecycleTestSub2ViewModel sub2ViewModel = viewTuple.getCodeBehind().sub2Controller.viewModel;

		VBox container = new VBox();

		Stage stage = new Stage();
		Scene scene = new Scene(container);
		stage.setScene(scene);


		// before adding to stage
		assertThat(rootViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(rootViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(sub1ViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(sub1ViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(sub2ViewModel.onViewAddedCalled).isEqualTo(0);
		assertThat(sub2ViewModel.onViewRemovedCalled).isEqualTo(0);

		// add rootView to container

		container.getChildren().add(viewTuple.getView());

		assertThat(rootViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(rootViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(sub1ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(sub1ViewModel.onViewRemovedCalled).isEqualTo(0);
		assertThat(sub2ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(sub2ViewModel.onViewRemovedCalled).isEqualTo(0);

		// remove from container
		container.getChildren().clear();


		assertThat(rootViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(rootViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(sub1ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(sub1ViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(sub2ViewModel.onViewAddedCalled).isEqualTo(1);
		assertThat(sub2ViewModel.onViewRemovedCalled).isEqualTo(1);


		// add again to container
		container.getChildren().add(viewTuple.getView());

		assertThat(rootViewModel.onViewAddedCalled).isEqualTo(2);
		assertThat(rootViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(sub1ViewModel.onViewAddedCalled).isEqualTo(2);
		assertThat(sub1ViewModel.onViewRemovedCalled).isEqualTo(1);
		assertThat(sub2ViewModel.onViewAddedCalled).isEqualTo(2);
		assertThat(sub2ViewModel.onViewRemovedCalled).isEqualTo(1);

	}

}
