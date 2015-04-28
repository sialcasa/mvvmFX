package usecases.nestedviewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author manuel.mauky
 */
public class SubSubViewModel implements ViewModel {

	private IntegerProperty number = new SimpleIntegerProperty();
	
	public IntegerProperty numberProperty(){
		return number;
	}
}
