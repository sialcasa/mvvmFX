package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.*;

import java.util.List;

public class ExampleModel {
	
	private IntegerProperty integerProperty = new SimpleIntegerProperty();
	private DoubleProperty doubleProperty = new SimpleDoubleProperty();
	private FloatProperty floatProperty = new SimpleFloatProperty();
	private LongProperty longProperty = new SimpleLongProperty();
	
	private StringProperty stringProperty = new SimpleStringProperty();
	
	private ObjectProperty<Person> objectProperty = new SimpleObjectProperty<>();
	
	private BooleanProperty booleanProperty = new SimpleBooleanProperty();

	private ListProperty<String> listProperty = new SimpleListProperty<>();
	
	
	public int getInteger() {
		return integerProperty.get();
	}
	
	public IntegerProperty integerProperty() {
		return integerProperty;
	}
	
	public void setInteger(int integerProperty) {
		this.integerProperty.set(integerProperty);
	}
	
	public double getDouble() {
		return doubleProperty.get();
	}
	
	public DoubleProperty doubleProperty() {
		return doubleProperty;
	}
	
	public void setDouble(double doubleProperty) {
		this.doubleProperty.set(doubleProperty);
	}
	
	public float getFloat() {
		return floatProperty.get();
	}
	
	public FloatProperty floatProperty() {
		return floatProperty;
	}
	
	public void setFloat(float floatProperty) {
		this.floatProperty.set(floatProperty);
	}
	
	public long getLong() {
		return longProperty.get();
	}
	
	public LongProperty longProperty() {
		return longProperty;
	}
	
	public void setLong(long longProperty) {
		this.longProperty.set(longProperty);
	}
	
	public String getString() {
		return stringProperty.get();
	}
	
	public StringProperty stringProperty() {
		return stringProperty;
	}
	
	public void setString(String stringProperty) {
		this.stringProperty.set(stringProperty);
	}
	
	public Person getObject() {
		return objectProperty.get();
	}
	
	public ObjectProperty<Person> objectProperty() {
		return objectProperty;
	}
	
	public void setObject(Person objectProperty) {
		this.objectProperty.set(objectProperty);
	}
	
	public boolean getBoolean() {
		return booleanProperty.get();
	}
	
	public BooleanProperty booleanProperty() {
		return booleanProperty;
	}
	
	public void setBoolean(boolean booleanProperty) {
		this.booleanProperty.set(booleanProperty);
	}

	public List<String> getList() {
		return listProperty.get();
	}

	public ListProperty<String> listProperty() {
		return listProperty;
	}

	public void setList(List<String> listProperty) {
		this.listProperty.setAll(listProperty);
	}
}
