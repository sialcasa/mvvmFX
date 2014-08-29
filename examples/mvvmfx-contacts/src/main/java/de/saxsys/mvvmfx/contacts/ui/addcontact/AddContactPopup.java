package de.saxsys.mvvmfx.contacts.ui.addcontact;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.contacts.events.OpenAddContactPopupEvent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AddContactPopup extends VBox implements FxmlView<AddContactPopupViewModel>{

	private Stage popupStage = new Stage(StageStyle.UTILITY);
	
	public AddContactPopup(){
		FluentViewLoader.fxmlView(this.getClass()).root(this).codeBehind(this).load();
	}
	
	
	public void open(@Observes OpenAddContactPopupEvent event){
		
		if(popupStage.getScene() == null){ // When the popup is shown the first time
			popupStage.setScene(new Scene(this));
		}else{
			popupStage.toFront();	
		}
		
		popupStage.show();
		
	}
}
