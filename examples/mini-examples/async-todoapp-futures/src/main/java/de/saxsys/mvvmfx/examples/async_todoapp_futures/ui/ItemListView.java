package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ItemListView implements FxmlView<ItemListViewModel> {

    @FXML
    public ListView<TodoItem> items;

    @InjectViewModel
    private ItemListViewModel viewModel;

    public void initialize() {
        items.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                return new ListCell<TodoItem>(){
                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            setText(item.getText());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        items.setItems(viewModel.itemsProperty());
        items.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            viewModel.selectedItemProperty().setValue(newValue);
        }));

        viewModel.selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            items.getSelectionModel().select(newValue);
        }));
    }
}