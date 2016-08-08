package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewNodeTestHelper {

	public static void check(ViewNode node, int numberOfChildren, Class<? extends View> viewType, Class<? extends ViewModel> viewModelType){
		if(viewType != null) {
			assertThat(node.getViewType().isPresent())
					.as("Expected viewNode to have viewType %s but no viewType was provided", viewType).isTrue();
			assertThat(node.getViewType().get()).isEqualTo(viewType);
		}

//		if(viewModelType != null) {
//			assertThat(node.getViewModelType().isPresent())
//					.as("Expected viewNode to have viewModelType %s but no viewModelType was provided", viewModelType).isTrue();
//			assertThat(node.getViewModelType().get()).isEqualTo(viewModelType);
//		}

		assertThat(node.getChildren()).hasSize(numberOfChildren);
	}
}
