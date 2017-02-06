package de.saxsys.mvvmfx.examples.async_todoapp_futures.model;


import javax.inject.Singleton;
import java.util.List;

@Singleton
public interface TodoItemService {

    void createItem(TodoItem item);

    void deleteItem(TodoItem item);

    TodoItem getItemById(String id);

    List<TodoItem> getAllItems();

}