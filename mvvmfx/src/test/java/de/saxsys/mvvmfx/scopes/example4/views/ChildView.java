package de.saxsys.mvvmfx.scopes.example4.views;

import de.saxsys.mvvmfx.*;

public class ChildView implements FxmlView<ChildViewModel> {

	@InjectViewModel
	ChildViewModel viewModel;


	public DialogView dialogView;

	@InjectContext
	Context context;

	public void initialize() {
		viewModel.subscribe(ChildViewModel.OPEN_DIALOG_MESSAGE, (k,payload) -> {
			ViewTuple<DialogView, DialogViewModel> viewTuple = FluentViewLoader.fxmlView(DialogView.class)
					.context(context)
					.load();

			dialogView = viewTuple.getCodeBehind();
		});
	}

}
