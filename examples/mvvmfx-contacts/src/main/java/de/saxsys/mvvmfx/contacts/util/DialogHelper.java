package de.saxsys.mvvmfx.contacts.util;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.function.Supplier;

public class DialogHelper {


	public static void initDialog(BooleanProperty openProperty, final Stage parentStage, Supplier<Parent> rootSupplier){
		final Stage dialogStage = new Stage(StageStyle.UTILITY);
		dialogStage.initOwner(parentStage);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		
		openProperty.addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				if(dialogStage.getScene() == null){
					Scene dialogScene = new Scene(rootSupplier.get());
					dialogScene.getStylesheets().add("/contacts.css");
					dialogStage.setScene(dialogScene);
				}else{
					dialogStage.toFront();
				}

				dialogStage.sizeToScene();
				dialogStage.show();
			} else {
				dialogStage.close();
			}
		});

		dialogStage.setOnCloseRequest(event -> openProperty.set(false));
	}
}
