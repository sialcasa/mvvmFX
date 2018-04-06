package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.testingutils.FxTestingUtils;
import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;

@ExtendWith(JfxToolkitExtension.class)
class ValidationVisualizerBaseTest {

    ValidationVisualizerBase cut;

    @Mock
    private ValidationStatus validationStatus;

    @Mock
    private Control control;

    // Can't Mock Optional final class
    @Mock
    private ValidationMessage highestMessageValue;
    private Optional<ValidationMessage> highestMessage;

    @Mock
    private ObservableList<ValidationMessage> messages;

    interface TestDummy {
        void applyRequiredVisualization(Control control, boolean required);
        void applyVisualization(Control control, Optional<ValidationMessage> messageOptional, boolean required);
    }

    @Mock
    TestDummy testDummy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        highestMessage = Optional.of(highestMessageValue); // cant mock optional final class

        cut = new ValidationVisualizerBase() {
            @Override
            void applyRequiredVisualization(Control control, boolean required) {
                testDummy.applyRequiredVisualization(control, required);
            }

            @Override
            void applyVisualization(Control control, Optional<ValidationMessage> messageOptional, boolean required) {
                testDummy.applyVisualization(control, messageOptional, required);
            }
        };

        Mockito.when(validationStatus.getHighestMessage()).thenReturn(highestMessage);
        Mockito.when(validationStatus.getMessages()).thenReturn(messages);

    }

    /*
    This test validates that initialization code is executed later on the Platform thread.
    The reason this must happen is that the initialization function can be called while the
    node graph is still being built. Adding Visualizer nodes during initialization can cause
    inconsistencies depending on when that happens. Running this code later ensures all
    initialization is completed before the visualizers are added.
     */
    @Test
    void testDelayedInitialization() throws InterruptedException {
        boolean required = true;
        cut.initVisualization(validationStatus, control, required);
        Semaphore semaphore = new Semaphore(0);
        FxTestingUtils.putPlatformThreadOnHold(semaphore);
        Mockito.verifyZeroInteractions(testDummy);
        semaphore.release();
        FxTestingUtils.waitForUiThread();
        Mockito.verify(testDummy).applyRequiredVisualization(control, required);
        Mockito.verify(testDummy).applyVisualization(control, highestMessage, required);
        Mockito.verify(messages).addListener(Mockito.any(ListChangeListener.class));
    }

    @Test
    void testDelayedInitializationWithRequiredFalse() throws InterruptedException {
        boolean required = false;
        cut.initVisualization(validationStatus, control, required);
        Semaphore semaphore = new Semaphore(0);
        FxTestingUtils.putPlatformThreadOnHold(semaphore);
        Mockito.verifyZeroInteractions(testDummy);
        semaphore.release();
        FxTestingUtils.waitForUiThread();
        Mockito.verify(testDummy, never()).applyRequiredVisualization(control, required);
        Mockito.verify(testDummy).applyVisualization(control, highestMessage, required);
        Mockito.verify(messages).addListener(Mockito.any(ListChangeListener.class));
    }
}