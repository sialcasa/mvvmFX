package de.saxsys.jfx.mvvm.utils.sizebinding;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindSize;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.unbindSize;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import org.junit.Before;
import org.junit.Test;


public class UnbindSizeTest extends SizeBindingsBuilderTestBase {
	
	private ImageView targetImageView;
	private Control targetControl;
	private Rectangle targetRectangle;
	private Region targetRegion;
	
	
	@Before
	public void setUp() {
		targetImageView = new ImageView();
		targetControl = new ScrollPane();
		targetRectangle = new Rectangle();
		targetRegion = new Region();
	}
	
	@Test
	public void unbindSizeOfImageView() {
		bindSize().from(fromRectangle).to(targetImageView);
		
		unbindSize().of(targetImageView);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetImageView); // still the old size
	}
	
	@Test
	public void unbindSizeOfControl() {
		bindSize().from(fromRectangle).to(targetControl);
		
		unbindSize().of(targetControl);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetControl); // still the old size
	}
	
	@Test
	public void unbindSizeOfRectangle() {
		bindSize().from(fromRectangle).to(targetRectangle);
		
		unbindSize().of(targetRectangle);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetRectangle); // still the old size
	}
	
	@Test
	public void unbindSizeOfRegion() {
		bindSize().from(fromRectangle).to(targetRegion);
		
		unbindSize().of(targetRegion);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		fromRectangle.setWidth(SIZEVAL + 100);
		
		assertCorrectSize(targetRegion); // still the old size
	}
}
