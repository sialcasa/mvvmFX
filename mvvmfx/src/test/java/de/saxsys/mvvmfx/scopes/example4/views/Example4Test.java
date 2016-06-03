package de.saxsys.mvvmfx.scopes.example4.views;

import com.cedarsoft.test.utils.CatchAllExceptionsRule;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.testingutils.FxTestingUtils;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class Example4Test {


	// Rule to get exceptions from the JavaFX Thread into the JUnit thread
	@Rule
	public CatchAllExceptionsRule catchAllExceptionsRule = new CatchAllExceptionsRule();


	@Test
	public void test() {

		ViewTuple<ChildView, ChildViewModel> viewTuple = FluentViewLoader.fxmlView(ChildView.class).load();

		ChildView codeBehind = viewTuple.getCodeBehind();
		ChildViewModel viewModel = viewTuple.getViewModel();

		DialogScope dialogScopeFromChildVM = viewModel.dialogScope;

		assertThat(codeBehind.dialogView).isNull();



		viewModel.openDialog();


		FxTestingUtils.waitForUiThread();


		assertThat(codeBehind.dialogView).isNotNull();

		DialogView dialogView = codeBehind.dialogView;
		DialogViewModel dialogViewModel = dialogView.viewModel;

		DialogScope dialogScopeFromDialogVM = dialogViewModel.scope;

		assertThat(dialogScopeFromDialogVM).isEqualTo(dialogScopeFromChildVM);
	}


}
