package de.saxsys.mvvmfx.utils.commands;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class CompositeCommand extends CommandBase {
	
	private final ObservableList<Command> registeredCommands = FXCollections.observableArrayList();
	
	
	public CompositeCommand(Command... commands) {
		initRegisteredCommandsListener();
		
		this.registeredCommands.addAll(commands);
	}
	
	private void initRegisteredCommandsListener() {
		this.registeredCommands.addListener((ListChangeListener<Command>) c -> {
			while (c.next()) {
				BooleanBinding binding = null;
				
				for (int i = 0; i < registeredCommands.size(); i++) {
					ReadOnlyBooleanProperty currentExecutable = registeredCommands.get(i).executeableProperty();
					if (binding == null) {
						binding = currentExecutable.and(currentExecutable);
					} else {
						binding = binding.and(currentExecutable);
					}
				}
				executeable.bind(binding);
			}
		});
	}
	
	public void register(Command command) {
		registeredCommands.add(command);
	}
	
	public void unregister(Command command) {
		registeredCommands.remove(command);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.saxsys.mvvmfx.utils.commands.Command#fire()
	 */
	@Override
	public void execute() {
		if (!isExecuteable()) {
			throw new RuntimeException("Not executable");
		} else {
			registeredCommands.forEach(t -> t.execute());
		}
	}
	
	
	
	
}
