package usecases.nestedviewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author manuel.mauky
 */
public class SubViewModel implements ViewModel {
	
	private SubSubViewModel subSubViewModel1;
	private SubSubViewModel subSubViewModel2;
	private SubSubViewModel subSubViewModel3;

	

	private IntegerProperty number = new SimpleIntegerProperty();

	public void init(){
		subSubViewModel1.numberProperty().bind(number.multiply(10).add(1));
		subSubViewModel2.numberProperty().bind(number.multiply(10).add(2));
		subSubViewModel3.numberProperty().bind(number.multiply(10).add(3));
	}
	
	public IntegerProperty numberProperty(){
		return number;
	}

	public void setSubSubViewModel1(SubSubViewModel subSubViewModel1) {
		this.subSubViewModel1 = subSubViewModel1;
	}

	public void setSubSubViewModel2(SubSubViewModel subSubViewModel2) {
		this.subSubViewModel2 = subSubViewModel2;
	}

	public void setSubSubViewModel3(SubSubViewModel subSubViewModel3) {
		this.subSubViewModel3 = subSubViewModel3;
	}
	
}
