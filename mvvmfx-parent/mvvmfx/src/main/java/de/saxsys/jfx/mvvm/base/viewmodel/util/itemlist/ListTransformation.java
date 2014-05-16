package de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Binds an {@link javafx.collections.ObservableList} that contains elements of TargetType to another
 * {@link javafx.collections.ObservableList} that contains elements of SourceType. This is different to the
 * normal list binding offered by JavaFX where the lists have to contain elements of the same type.
 */
public class ListTransformation<SourceType, TargetType> {

    // Converter
    private final Function<SourceType, TargetType> function;

    // The two lists - List which was provided and the String representation of
    // the list
    private ReadOnlyListWrapper<TargetType> viewModelList = new ReadOnlyListWrapper<>(
            FXCollections.<TargetType>observableArrayList());
    private ListProperty<SourceType> modelList = new SimpleListProperty<>();

    // Reference to the listener to use it by a wrapped listchangelistener
    private ListChangeListener<SourceType> listChangeListener;

    /**
     * Creates a {@link ListTransformation} by a given list of items and a function.
     *
     * @param modelList which should be transformed for the UI
     * @param function    which is used for transformation
     */
    public ListTransformation(ObservableList<SourceType> modelList,
                              final Function<SourceType, TargetType> function) {
        this.function = function;
        initListEvents();
        this.modelListProperty().set(modelList);
    }

    /**
     * Creates a {@link ListTransformation} by with a given function.
     *
     * @param function which is used for transformation
     */
    public ListTransformation(final Function<SourceType, TargetType> function) {
        this(FXCollections.<SourceType>emptyObservableList(), function);
    }

    // If the list changed we want the recreate the string
    private void initListEvents() {
        this.listChangeListener = new ListChangeListener<SourceType>() {
            @Override
            public void onChanged(
                    Change<? extends SourceType> listEvent) {

                // We have to stage delete events, because if we process them
                // separatly, there will be unwanted Changeevents on the
                // stringlist
                List<TargetType> deleteStaging = new ArrayList<>();

                while (listEvent.next()) {
                    if (listEvent.wasUpdated()) {
                        processUpdateEvent(listEvent);
                    } else if (listEvent.wasReplaced()) {
                        processReplaceEvent(listEvent, deleteStaging);
                    } else if (listEvent.wasAdded()) {
                        processAddEvent(listEvent);
                    } else if (listEvent.wasRemoved()) {
                        processRemoveEvent(listEvent, deleteStaging);
                    }
                }

                // Process the staged elements
                processStagingLists(deleteStaging);
            }
        };
        modelListProperty().addListener(
                new WeakListChangeListener<>(listChangeListener));

    }

    /**
     * Maps an add event of the model list to new elements of the
     * {@link #viewModelList}.
     *
     * @param listEvent to analyze
     */
    private void processAddEvent(
            ListChangeListener.Change<? extends SourceType> listEvent) {
        for (int i = listEvent.getFrom(); i < listEvent.getTo(); i++) {
            SourceType item = listEvent.getList().get(i);
            viewModelList.add(i, ListTransformation.this.function.apply(item));
        }
    }

    /**
     * Maps an remove event of the model list to new elements of the
     * {@link #viewModelList}.
     *
     * @param listEvent     to process
     * @param deleteStaging for staging the delete events
     */
    private void processRemoveEvent(
            ListChangeListener.Change<? extends SourceType> listEvent,
            List<TargetType> deleteStaging) {
        for (int i = 0; i < listEvent.getRemovedSize(); i++) {
            deleteStaging.add(viewModelList.get(listEvent.getFrom() + i));
        }
    }

    /**
     * Maps an update event of the model list to new elements of the
     * {@link #viewModelList}.
     *
     * @param listEvent to process
     */
    private void processUpdateEvent(ListChangeListener.Change<? extends SourceType> listEvent) {
        for (int i = listEvent.getFrom(); i < listEvent.getTo(); i++) {
            SourceType item = listEvent.getList().get(i);
            viewModelList.set(i, ListTransformation.this.function.apply(item));
        }
    }

    /**
     * Maps an replace event of the model list to new elements of the
     * {@link #viewModelList}.
     *
     * @param listEvent to process
     */
    private void processReplaceEvent(
            ListChangeListener.Change<? extends SourceType> listEvent, List<TargetType> deletedStaging) {
        processRemoveEvent(listEvent, deletedStaging);
        processStagingLists(deletedStaging);
        processAddEvent(listEvent);
    }

    /**
     * Process staging events.
     *
     * @param deleteStaging to process
     */
    private void processStagingLists(List<TargetType> deleteStaging) {
        viewModelList.removeAll(deleteStaging);
        deleteStaging.clear();
    }

    /**
     * @return List of elements which should be transformed.
     */
    public ListProperty<SourceType> modelListProperty() {
        return modelList;
    }

    /**
     * Set the model list that should be synchronized with the target list.
     *
     * @param modelList the source list
     */
    public void setModelList(ObservableList<SourceType> modelList) {
        this.modelList.set(modelList);
    }

    /**
     * @return String representation of {@link #modelListProperty()}.
     */
    public ReadOnlyListProperty<TargetType> targetListProperty() {
        return viewModelList.getReadOnlyProperty();
    }

}
