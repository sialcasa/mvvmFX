package de.saxsys.mvvmfx.examples.async_todoapp_futures.model;


import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TodoItemServiceImpl implements TodoItemService {

    /*
     * To simulate a "real" backend service, this constant defines
     * the amount of time that this service will take to answer requests.
     */
    private final static int DELAY_IN_MS = 400;

    private List<TodoItem> items = new ArrayList<>();

    @Override
    public void createItem(TodoItem item) {
        delay();
        throwRandomError();
        if(!items.contains(item)) {
            items.add(item);
        }
    }

    @Override
    public void deleteItem(TodoItem item) {
        delay();
        throwRandomError();
        items.remove(item);
    }

    @Override
    public TodoItem getItemById(String id) {
        delay();
        throwRandomError();
        return items.stream()
            .filter(item -> item.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<TodoItem> getAllItems() {
        delay();
        return new ArrayList<>(items);
    }

    private void delay() {
        if(DELAY_IN_MS > 0) {
            try{
                Thread.sleep(DELAY_IN_MS);
            }  catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void throwRandomError() {
        final double rand = Math.random();

        if(rand > 0.8) {
            throw new RuntimeException("Connection Timeout");
        }
    }
}
