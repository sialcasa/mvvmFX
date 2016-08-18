package de.saxsys.mvvmfx.internal.viewloader.prevent_gc;

import de.saxsys.mvvmfx.FluentViewLoader;
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


	@Test
	@TestInJfxThread
	public void testWithoutGC() {
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


	@Ignore("until fixed")
	@Test
	@TestInJfxThread
	public void testWithGC() {
		Parent root = FluentViewLoader.fxmlView(PreventGcTestView.class).load().getView();
		TextField input = lookup("input", root, TextField.class);
		Label output = lookup("output", root, Label.class);

		// given
		assertThat(input.getText()).isNullOrEmpty();
		assertThat(output.getText()).isNullOrEmpty();

		GCVerifier.forceGC();

		// when
		input.setText("Hello");

		// then
		assertThat(output.getText()).isEqualTo("Hello");
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
