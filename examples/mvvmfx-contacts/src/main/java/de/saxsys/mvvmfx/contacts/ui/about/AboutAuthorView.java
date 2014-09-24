package de.saxsys.mvvmfx.contacts.ui.about;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.contacts.events.OpenAuthorPageEvent;
import de.saxsys.mvvmfx.contacts.util.DialogHelper;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class AboutAuthorView implements FxmlView<AboutAuthorViewModel> {


	private Parent root;
	
	@Inject
	private Stage primaryStage;
	
	
	@InjectViewModel
	private AboutAuthorViewModel viewModel;
	
	AboutAuthorView(){
		root = FluentViewLoader.fxmlView(this.getClass()).codeBehind(this).load().getView();
	}
	
	public void initialize(){
		DialogHelper.initDialog(viewModel.dialogOpenProperty(), primaryStage, ()->root);
	}
	
	public void openDialog(@Observes OpenAuthorPageEvent event){
		viewModel.openDialog();
	}
	
	@FXML
	public void openBlog(){
		viewModel.openBlog();
	}
	
	@FXML
	public void openTwitter(){
		viewModel.openTwitter();
	}
}
