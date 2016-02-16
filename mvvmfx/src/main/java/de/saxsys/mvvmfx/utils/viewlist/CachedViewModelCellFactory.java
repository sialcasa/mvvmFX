/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.viewlist;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.JavaView;
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
 * An implementation of the {@link ViewListCellFactory} that can be used for {@link ListView}s that are based on a list
 * of ViewModels. Additionally this CellFactory has a cache for {@link ViewTuple}s that where already loaded before.
 * <br>
 * <br>
 * 
 * This can be useful because the ListView can call the CellFactory not only when the items list changes but also on
 * other events like scrolling. Without a cache a new View would be loaded each time. This can cause unwanted effects
 * like view items that are not responding to clicks on controls in the item view. <br>
 * <br>
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
 *  		itemList.setCellFactory(CachedViewModelCellFactory.createForFxmlView(ItemView.class));
 *     }
 * }
 * </pre>
 * 
 * The example above uses the {@link #createForFxmlView(Class)} factory method for a specific View type.
 * 
 * <br>
 * 
 * If you need more control over the loading process (like providing custom resourceBundles) you can use the
 * {@link #create(Callback)} method instead. This method takes a {@link Callback} as argument. This callback gets an
 * instance of the {@link ViewModel} as argument and has to return a {@link ViewTuple} for this viewModel. This means
 * that you have to call the {@link FluentViewLoader} by yourself.
 * 
 * <br>
 * 
 * See the following example which is equivalent to the one above:
 * 
 * <pre>
 * itemList.setCellFactory(CachedViewModelCellFactory.create(
 * 		vm -> FluentViewLoader.fxmlView(ItemView.class).viewModel(vm).load()));
 * </pre>
 * 
 * 
 * @author manuel.mauky
 */
@Beta
public class CachedViewModelCellFactory<V extends View<VM>, VM extends ViewModel> implements ViewListCellFactory<VM> {
	
	private Map<VM, ViewTuple<V, VM>> cache = new HashMap<>();
	
	private Callback<VM, ViewTuple<V, VM>> loadFactory;
	
	public CachedViewModelCellFactory(Callback<VM, ViewTuple<V, VM>> loadFactory) {
		this.loadFactory = loadFactory;
	}
	
	@Override
	public ViewTuple<V, VM> map(VM viewModel) {
		if (!cache.containsKey(viewModel)) {
			final ViewTuple<V, VM> viewTuple = loadFactory.call(viewModel);
			cache.put(viewModel, viewTuple);
		}
		
		return cache.get(viewModel);
	}
	
	
	public static <V extends View<VM>, VM extends ViewModel> CachedViewModelCellFactory<V, VM> create(
			Callback<VM, ViewTuple<V, VM>> callback) {
		return new CachedViewModelCellFactory<>(callback);
	}
	
	public static <V extends FxmlView<VM>, VM extends ViewModel> CachedViewModelCellFactory<V, VM> createForFxmlView(
			Class<V> viewType) {
		return create(vm -> FluentViewLoader.fxmlView(viewType).viewModel(vm).load());
	}
	
	public static <V extends JavaView<VM>, VM extends ViewModel> CachedViewModelCellFactory<V, VM> createForJavaView(
			Class<V> viewType) {
		return create(vm -> FluentViewLoader.javaView(viewType).viewModel(vm).load());
	}
	
}
