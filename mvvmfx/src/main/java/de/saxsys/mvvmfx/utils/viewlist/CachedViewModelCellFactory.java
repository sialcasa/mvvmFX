package de.saxsys.mvvmfx.utils.viewlist;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.View;
import eu.lestard.doc.Beta;
import javafx.util.Callback;

import javax.swing.text.html.ListView;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * An implementation of the {@link ViewListCellFactory} that can be used for {@link ListView}s that are based 
 * on a list of ViewModels. Additionally this CellFactory has a cache for {@link ViewTuple}s that where already
 * loaded before. <br><br>
 *     
 * This can be useful because the ListView can call the CellFactory not only when the items list changes but also on other
 * events like scrolling. Without a cache a new View would be loaded each time. This can cause unwanted effects like
 * view items that are not responding to clicks on controls in the item view. 
 * <br><br>
 * Typical useage:
 * 
 * <pre>
 * public class OverviewView implements FxmlView{@code <OverviewViewModel>} {
 *     
 *     {@literal @}FXML
 *     public ListView{@code <ItemViewModel>} itemList;
 *     {@literal @}InjectViewModel
 *     private OverviewViewModel viewModel;
 *     
 *     public void initialize(){
 *  		itemList.setItems(viewModel.itemsProperty());
 *  	
 *  		itemList.setCellFactory(CachedViewModelCellFactory.create(
 *  			vm -> FluentViewLoader.fxmlView(ItemView.class).viewModel(vm).load()));	
 *     }
 * }
 * 
 * </pre>
 * 
 * 
 * 
 * @author manuel.mauky
 */
@Beta
public class CachedViewModelCellFactory<V extends View<VM>, VM extends ViewModel> implements ViewListCellFactory<VM>{
	
	private Map<VM, ViewTuple<V, VM>> cache = new HashMap<>();
	
	private Callback<VM, ViewTuple<V, VM>> loadFactory;

	private CachedViewModelCellFactory(Callback<VM, ViewTuple<V, VM>> loadFactory) {
		this.loadFactory = loadFactory;
	}

	@Override
	public ViewTuple<V, VM> map(VM viewModel) {
		if(! cache.containsKey(viewModel)){
			final ViewTuple<V, VM> viewTuple = loadFactory.call(viewModel);
			cache.put(viewModel, viewTuple);
		}
		
		return cache.get(viewModel);
	}
	
	
	public static <V extends View<VM>, VM extends ViewModel> CachedViewModelCellFactory<V,VM> create(Callback<VM, ViewTuple<V,VM>> callback) {
		return new CachedViewModelCellFactory<>(callback) ;
	}
}
