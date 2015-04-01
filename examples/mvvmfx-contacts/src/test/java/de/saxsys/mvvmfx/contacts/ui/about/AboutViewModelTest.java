package de.saxsys.mvvmfx.contacts.ui.about;

import javafx.beans.property.ReadOnlyStringProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AboutViewModelTest {
	
	private static final String MY_COOL_LIB_NAME = "my cool library";
	private static final String MY_COOL_LIB_URL = "http://my-cool-library.example.org";
	
	private static final String OTHER_FX_NAME = "otherFX";
	private static final String OTHER_FX_URL = "http://otherfx.example.org";
	
	private AboutViewModel viewModel;
	
	private Consumer<String> onLinkClickedHandler;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		viewModel = new AboutViewModel();
		
		onLinkClickedHandler = mock(Consumer.class);
		viewModel.onLinkClickedHandler = onLinkClickedHandler;
	}
	
	@Test
	public void testLibrariesLabel() {
		
		ReadOnlyStringProperty libraries = viewModel.librariesLabelTextProperty();
		
		assertThat(libraries).hasValue("");
		
		viewModel.libraryLinkMap.put(MY_COOL_LIB_NAME, MY_COOL_LIB_URL);
		
		assertThat(libraries).hasValue("- [my cool library]\n");
		
		viewModel.libraryLinkMap.put(OTHER_FX_NAME, OTHER_FX_URL);
		assertThat(libraries).hasValue("- [my cool library]\n- [otherFX]\n");
	}
	
	@Test
	public void testOnLinkClicked() {
		
		viewModel.libraryLinkMap.put(MY_COOL_LIB_NAME, MY_COOL_LIB_URL);
		viewModel.libraryLinkMap.put(OTHER_FX_NAME, OTHER_FX_URL);
		
		viewModel.onLinkClicked(MY_COOL_LIB_NAME);
		
		verify(onLinkClickedHandler).accept(MY_COOL_LIB_URL);
		
		viewModel.onLinkClicked(OTHER_FX_NAME);
		verify(onLinkClickedHandler).accept(OTHER_FX_URL);
		
		viewModel.onLinkClicked("something else");
		
		verifyNoMoreInteractions(onLinkClickedHandler);
	}
}
