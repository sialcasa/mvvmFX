package de.saxsys.mvvmfx.scopes.example1;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.scopes.example1.views.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.omg.SendingContext.RunTime;

public class Example1ScopesTest {


	@Test
	public void testJavaScopedView() throws Exception {

		// FIXME JAVA TESTS

		// Muss äquivalent zum FXML-Test sein und auch den Concern des Manuell
		// erstellten ViewModels testen

		// final ScopedViewModelA viewModelA =
		// FluentViewLoader.javaView(ScopedJavaViewA.class).load().getViewModel();
		// final ScopedViewModelB viewModelB =
		// FluentViewLoader.javaView(ScopedJavaViewB.class).load().getViewModel();
		//
		// verifyScopes(viewModelA, viewModelB);
	}


	@Test
	public void testFxmlScopedView() throws Exception {

		final ScopesFxmlParentView parentView = FluentViewLoader.fxmlView(ScopesFxmlParentView.class)
				.load()
				.getCodeBehind();

		final ScopedViewModelA viewModelA = parentView.subviewAController.viewModel;
		final ScopedViewModelB viewModelB = parentView.subviewBController.viewModel;

		ScopedViewModelC viewModelCinA = parentView.subviewAController.subviewCController.viewModel;
		ScopedViewModelD viewModelDinA = parentView.subviewAController.subviewCController.subViewDController.viewModel;

		ScopedViewModelC viewModelCinB = parentView.subviewBController.subviewCController.viewModel;
		ScopedViewModelD viewModelDinB = parentView.subviewBController.subviewCController.subViewDController.viewModel;


		ScopedViewModelE viewModel_A_E = parentView.subviewAController.subviewEController.viewModel;
		ScopedViewModelF viewModel_A_E_F = parentView.subviewAController.subviewEController.subviewFController.viewModel;
		ScopedViewModelG viewModel_A_E_G = parentView.subviewAController.subviewEController.subviewGController.viewModel;

		ScopedViewModelE viewModel_B_E = parentView.subviewBController.subviewEController.viewModel;
		ScopedViewModelF viewModel_B_E_F = parentView.subviewBController.subviewEController.subviewFController.viewModel;
		ScopedViewModelG viewModel_B_E_G = parentView.subviewBController.subviewEController.subviewGController.viewModel;


		Assertions.assertNotNull(viewModel_A_E);
		Assertions.assertNotNull(viewModel_A_E_F);
		Assertions.assertNotNull(viewModel_A_E_G);
		Assertions.assertNotNull(viewModel_B_E);
		Assertions.assertNotNull(viewModel_B_E_F);
		Assertions.assertNotNull(viewModel_B_E_G);


		Assertions.assertNotEquals(viewModel_A_E.testScope3, viewModel_B_E.testScope3);

		Assertions.assertEquals(viewModel_A_E.testScope3, viewModel_A_E_F.testScope3);
		Assertions.assertEquals(viewModel_A_E.testScope3, viewModel_A_E_G.testScope3);

		Assertions.assertEquals(viewModel_B_E.testScope3, viewModel_B_E_F.testScope3);
		Assertions.assertEquals(viewModel_B_E.testScope3, viewModel_B_E_G.testScope3);



		verifyScopes(viewModelA, viewModelB, viewModelCinA, viewModelCinB, viewModelDinA, viewModelDinB);
	}

	@Test
	public void testErrorWhenNoScopeProviderFound() {

		final ScopesFxmlParentView parentView = FluentViewLoader.fxmlView(ScopesFxmlParentView.class)
				.load()
				.getCodeBehind();

		Assertions.assertThrows(Exception.class, () ->{
			parentView.subviewAController.subviewCController.loadWrongScopedView();
		});
	}

	private void verifyScopes(ScopedViewModelA viewModelA, ScopedViewModelB viewModelB, ScopedViewModelC viewModelCinA,
							  ScopedViewModelC viewModelCinB, ScopedViewModelD viewModelDinA, ScopedViewModelD viewModelDinB) {

		Assertions.assertNotNull(viewModelA);
		Assertions.assertNotNull(viewModelB);
		Assertions.assertNotNull(viewModelCinA);
		Assertions.assertNotNull(viewModelCinB);
		Assertions.assertNotNull(viewModelDinA);
		Assertions.assertNotNull(viewModelDinB);

		Assertions.assertNotNull(viewModelA.injectedScope1);
		Assertions.assertNotNull(viewModelB.injectedScope1);
		Assertions.assertNotNull(viewModelCinA.injectedScope1);
		Assertions.assertNotNull(viewModelCinB.injectedScope1);
		Assertions.assertNotNull(viewModelDinA.injectedScope1);
		Assertions.assertNotNull(viewModelDinA.injectedScope2);
		Assertions.assertNotNull(viewModelDinB.injectedScope1);
		Assertions.assertNotNull(viewModelDinB.injectedScope2);


		Assertions.assertNotEquals(viewModelA.injectedScope1, viewModelB.injectedScope1);

		Assertions.assertEquals(viewModelA.injectedScope1, viewModelCinA.injectedScope1);
		Assertions.assertEquals(viewModelA.injectedScope1, viewModelDinA.injectedScope1);

		Assertions.assertEquals(viewModelB.injectedScope1, viewModelCinB.injectedScope1);
		Assertions.assertEquals(viewModelB.injectedScope1, viewModelDinB.injectedScope1);

	}

}
