package de.saxsys.mvvmfx.utils.items;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 * This interface belongs to the {@link ItemList} class.
 * See {@link ItemList} for how to use the itemList. <p/>
 * This interface is used to hide the implementation details of the {@link ItemList}
 * from the View. The instance of {@link ItemList} will be hold in the ViewModel.
 * However, to connect UI controls the itemList has to somehow be accessible by the View.
 * For this purpose this interface is used. Instead of providing the actual {@link ItemList}
 * from the ViewModel to the View you can instead use this interface type for the getter method.
 * This way the View can only access and use the methods that are needed to connect the UI controls.
 * <p/>
 * Typical usage:
 * <pre>
*
 *     // in the ViewModel
 *
 *     private ItemList{@code <Person, Long>} itemList = new{@code ItemList<>}(Person::getId);
 *
 *
 *     public ViewItemList{@code <Long>} getItemList() {
 *         return itemList;
 *     }
 *
 *
 *     // in the View
 *
 *    {@literal @}FXML
 *     private ListView{@code <Long>} listView;
 *
 *    {@literal @}InjectViewModel}
 *     private MyViewModel viewModel;
 *
 *     public void initialize() {
 *         viewModel.getItemList().connect(listView);
 *     }
 *
 * </pre>
 *
 * @param <K>
 */
public interface ViewItemList <K> {


	Runnable connect(ComboBox<K> comboBox);

	Runnable connect(ListView<K> listView);

	Runnable connect(ChoiceBox<K> choiceBox);

}
