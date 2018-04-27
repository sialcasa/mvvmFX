package de.saxsys.mvvmfx.scopes.example4.views;

import com.cedarsoft.test.utils.CatchAllExceptionsRule;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.testingutils.FxTestingUtils;
import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(JfxToolkitExtension.class)
public class Example4Test {


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
