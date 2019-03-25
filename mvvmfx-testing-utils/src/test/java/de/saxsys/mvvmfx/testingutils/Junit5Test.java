package de.saxsys.mvvmfx.testingutils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javafx.application.Platform;


class Junit5Test {

	@ExtendWith(JfxToolkitExtension.class)
	@Nested
	class OnFxThread {
		@Test
		void testFxThreadIsAvailable() throws Exception {
			assertThat(Platform.isFxApplicationThread()).isFalse();
			CompletableFuture<Boolean> isInApplicationThread = new CompletableFuture<>();
			Platform.runLater(() -> {
				isInApplicationThread.complete(Platform.isFxApplicationThread());
			});
			assertThat(isInApplicationThread.get()).isTrue();
		}


		@Test
		void testOnFxThreadSuccess () {
			FxTestingUtils.runInFXThread(() -> {
				assertThat(Platform.isFxApplicationThread()).isTrue();
			});
		}

		@Test
		void testOnFxThreadFailed () {
			Assertions.assertThrows(AssertionError.class, () -> {
				FxTestingUtils.runInFXThread(() -> {
					fail("some reason");
				});
			}, "some reason");
		}
	}

	@Nested
	class NotOnFxThread {

		@Test
		void testFxThreadIsNotAvailable() {
			assertThat(Platform.isFxApplicationThread()).isFalse();

			Assertions.assertThrows(IllegalStateException.class, () -> {
				Platform.runLater(() -> {});
			}, "Toolkit not initialized");
		}
	}
}
