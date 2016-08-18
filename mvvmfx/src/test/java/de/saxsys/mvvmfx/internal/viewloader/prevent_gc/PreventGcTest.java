package de.saxsys.mvvmfx.internal.viewloader.prevent_gc;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.prevent_gc.example1.PreventGc2TestView;
import de.saxsys.mvvmfx.internal.viewloader.prevent_gc.example1.PreventGcTestView;
import de.saxsys.mvvmfx.testingutils.GCVerifier;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import de.saxsys.mvvmfx.testingutils.jfxrunner.TestInJfxThread;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class PreventGcTest {


	/**
	 * This test scenario shows a view without the {@link de.saxsys.mvvmfx.PreventGarbageCollection}
	 * interface.
	 * In this example no garbage collection is done so everything works as expected.
	 */
	@Test
	@TestInJfxThread
	public void testWithoutGcWithoutPrevention() {
		Parent root = FluentViewLoader.fxmlView(PreventGcTestView.class).load().getView();
		TextField input = lookup("input", root, TextField.class);
		Label output = lookup("output", root, Label.class);

		// given
		assertThat(input.getText()).isNullOrEmpty();
		assertThat(output.getText()).isNullOrEmpty();


		// when
		input.setText("Hello");

		// then
		assertThat(output.getText()).isEqualTo("Hello");
	}

	/**
	 * This test scenario shows a view without the {@link de.saxsys.mvvmfx.PreventGarbageCollection}
	 * interface.
	 * In this example we do force garbage collection. This leads to unexpected behavior because
	 * the CodeBehind and the ViewModel are removed and the binding is removed too.
	 */
	@Test
	@TestInJfxThread
	public void testWithGcWithoutPrevention() {
		Parent root = FluentViewLoader.fxmlView(PreventGcTestView.class).load().getView();
		TextField input = lookup("input", root, TextField.class);
		Label output = lookup("output", root, Label.class);

		// given
		assertThat(input.getText()).isNullOrEmpty();
		assertThat(output.getText()).isNullOrEmpty();


		// perform garbage collection
		GCVerifier.forceGC();

		// when
		input.setText("Hello");

		// then
		assertThat(output.getText()).isNullOrEmpty(); // still null because VM was collected
	}

	/**
	 * This test scenario shows a view with the {@link de.saxsys.mvvmfx.PreventGarbageCollection}
	 * interface.
	 * In this example we do force garbage collection but due to the prevention mechanism of the framework,
	 * the CodeBehind and ViewModel are not removed and everything works as expected.
	 */
	@Test
	@TestInJfxThread
	public void testWithGcWithPrevention() {
		Parent root = FluentViewLoader.fxmlView(PreventGc2TestView.class).load().getView();
		TextField input = lookup("input", root, TextField.class);
		Label output = lookup("output", root, Label.class);

		// given
		assertThat(input.getText()).isNullOrEmpty();
		assertThat(output.getText()).isNullOrEmpty();


		// perform garbage collection
		GCVerifier.forceGC();

		// when
		input.setText("Hello");

		// then
		assertThat(output.getText()).isEqualTo("Hello"); // works as expected
	}

	private <T extends Node> T lookup(String id, Parent parent, Class<T> type) {
		Optional<T> nodeOptional = parent.getChildrenUnmodifiable()
				.stream()
				.filter(child -> child.getId().equals(id))
				.findFirst()
				.filter(node -> type.isAssignableFrom(node.getClass()))
				.map(node -> (T) node);

		if(nodeOptional.isPresent()) {
			return nodeOptional.get();
		} else {
			throw new AssertionError("Cannot find node with id=" + id + " and type=" + type.getSimpleName());
		}
	}

}
