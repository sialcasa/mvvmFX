package de.saxsys.mvvmfx.examples.async_todoapp_futures.model;

import java.util.Objects;
import java.util.UUID;

public class TodoItem {

    private final String id;

    private String text;

    public TodoItem(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return Objects.equals(id, todoItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
