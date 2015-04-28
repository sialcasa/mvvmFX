package usecases.nestedviewmodels;

import de.saxsys.mvvmfx.ViewModel;

/**
 * @author manuel.mauky
 */
public class MainViewModel implements ViewModel {
	private SubViewModel subViewModel1;
	private SubViewModel subViewModel2;
	
	public void init(){
		subViewModel1.numberProperty().setValue(1);
		subViewModel2.numberProperty().setValue(2);
	}

	public void setSubViewModel1(SubViewModel subViewModel1) {
		this.subViewModel1 = subViewModel1;
	}

	public void setSubViewModel2(SubViewModel subViewModel2) {
		this.subViewModel2 = subViewModel2;
	}
}
